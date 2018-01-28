package jcifs.smb;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Random;
import jcifs.Config;
import jcifs.util.DES;
import jcifs.util.Encdec;
import jcifs.util.HMACT64;
import jcifs.util.LogStream;
import jcifs.util.MD4;

public final class NtlmPasswordAuthentication
  implements Principal, Serializable
{
  private final int LM_COMPATIBILITY = Config.getInt("jcifs.smb.lmCompatibility", 3);
  private static final Random RANDOM = new Random();
  private static LogStream log = LogStream.getInstance();
  private static final byte[] S8 = { 75, 71, 83, 33, 64, 35, 36, 37 };
  static String DEFAULT_DOMAIN;
  static String DEFAULT_USERNAME;
  static String DEFAULT_PASSWORD;
  static final String BLANK = "";
  public static final NtlmPasswordAuthentication ANONYMOUS = new NtlmPasswordAuthentication("", "", "");
  static final NtlmPasswordAuthentication NULL = new NtlmPasswordAuthentication("", "", "");
  static final NtlmPasswordAuthentication GUEST = new NtlmPasswordAuthentication("?", "GUEST", "");
  static final NtlmPasswordAuthentication DEFAULT = new NtlmPasswordAuthentication(null);
  String domain;
  String username;
  String password;
  byte[] ansiHash;
  byte[] unicodeHash;
  boolean hashesExternal = false;
  byte[] clientChallenge = null;
  byte[] challenge = null;

  private static void E(byte[] key, byte[] data, byte[] e)
  {
    byte[] key7 = new byte[7];
    byte[] e8 = new byte[8];

    for (int i = 0; i < key.length / 7; ++i) {
      System.arraycopy(key, i * 7, key7, 0, 7);
      DES des = new DES(key7);
      des.encrypt(data, e8);
      System.arraycopy(e8, 0, e, i * 8, 8);
    }
  }

  static void initDefaults()
  {
    if (DEFAULT_DOMAIN != null) return;
    DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", "?");
    DEFAULT_USERNAME = Config.getProperty("jcifs.smb.client.username", "GUEST");
    DEFAULT_PASSWORD = Config.getProperty("jcifs.smb.client.password", "");
  }

  public static byte[] getPreNTLMResponse(String password, byte[] challenge)
  {
    byte[] passwordBytes;
    byte[] p14 = new byte[14];
    byte[] p21 = new byte[21];
    byte[] p24 = new byte[24];
    try
    {
      passwordBytes = password.toUpperCase().getBytes(ServerMessageBlock.OEM_ENCODING);
    } catch (UnsupportedEncodingException uee) {
      throw new RuntimeException("Try setting jcifs.encoding=US-ASCII", uee);
    }
    int passwordLength = passwordBytes.length;

    if (passwordLength > 14)
      passwordLength = 14;

    System.arraycopy(passwordBytes, 0, p14, 0, passwordLength);
    E(p14, S8, p21);
    E(p21, challenge, p24);
    return p24;
  }

  public static byte[] getNTLMResponse(String password, byte[] challenge)
  {
    byte[] uni = null;
    byte[] p21 = new byte[21];
    byte[] p24 = new byte[24];
    try
    {
      uni = password.getBytes("UTF-16LE");
    } catch (UnsupportedEncodingException uee) {
      if (LogStream.level > 0)
        uee.printStackTrace(log);
    }
    MD4 md4 = new MD4();
    md4.update(uni);
    try {
      md4.digest(p21, 0, 16);
    } catch (Exception ex) {
      if (LogStream.level > 0)
        ex.printStackTrace(log);
    }
    E(p21, challenge, p24);
    return p24;
  }

  public static byte[] getLMv2Response(String domain, String user, String password, byte[] challenge, byte[] clientChallenge)
  {
    byte[] hash;
    try
    {
      hash = new byte[16];
      byte[] response = new byte[24];

      MD4 md4 = new MD4();
      md4.update(password.getBytes("UTF-16LE"));
      HMACT64 hmac = new HMACT64(md4.digest());
      hmac.update(user.toUpperCase().getBytes("UTF-16LE"));
      hmac.update(domain.toUpperCase().getBytes("UTF-16LE"));
      hmac = new HMACT64(hmac.digest());
      hmac.update(challenge);
      hmac.update(clientChallenge);
      hmac.digest(response, 0, 16);
      System.arraycopy(clientChallenge, 0, response, 16, 8);
      return response;
    } catch (Exception ex) {
      if (LogStream.level > 0)
        ex.printStackTrace(log); 
    }
    return null;
  }

  public static byte[] getNTLM2Response(byte[] nTOWFv1, byte[] serverChallenge, byte[] clientChallenge)
  {
    byte[] sessionHash = new byte[8];
    try
    {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(serverChallenge);
      md5.update(clientChallenge, 0, 8);
      System.arraycopy(md5.digest(), 0, sessionHash, 0, 8);
    } catch (GeneralSecurityException gse) {
      if (LogStream.level > 0)
        gse.printStackTrace(log);
      throw new RuntimeException("MD5", gse);
    }

    byte[] key = new byte[21];
    System.arraycopy(nTOWFv1, 0, key, 0, 16);
    byte[] ntResponse = new byte[24];
    E(key, sessionHash, ntResponse);

    return ntResponse;
  }

  public static byte[] nTOWFv1(String password) {
    if (password == null)
      throw new RuntimeException("Password parameter is required");
    try {
      MD4 md4 = new MD4();
      md4.update(password.getBytes("UTF-16LE"));
      return md4.digest();
    } catch (UnsupportedEncodingException uee) {
      throw new RuntimeException(uee.getMessage()); }
  }

  public static byte[] nTOWFv2(String domain, String username, String password) {
    MD4 md4;
    try {
      md4 = new MD4();
      md4.update(password.getBytes("UTF-16LE"));
      HMACT64 hmac = new HMACT64(md4.digest());
      hmac.update(username.toUpperCase().getBytes("UTF-16LE"));
      hmac.update(domain.getBytes("UTF-16LE"));
      return hmac.digest();
    } catch (UnsupportedEncodingException uee) {
      throw new RuntimeException(uee.getMessage());
    }
  }

  static byte[] computeResponse(byte[] responseKey, byte[] serverChallenge, byte[] clientData, int offset, int length)
  {
    HMACT64 hmac = new HMACT64(responseKey);
    hmac.update(serverChallenge);
    hmac.update(clientData, offset, length);
    byte[] mac = hmac.digest();
    byte[] ret = new byte[mac.length + clientData.length];
    System.arraycopy(mac, 0, ret, 0, mac.length);
    System.arraycopy(clientData, 0, ret, mac.length, clientData.length);
    return ret;
  }

  public static byte[] getLMv2Response(byte[] responseKeyLM, byte[] serverChallenge, byte[] clientChallenge)
  {
    return computeResponse(responseKeyLM, serverChallenge, clientChallenge, 0, clientChallenge.length);
  }

  public static byte[] getNTLMv2Response(byte[] responseKeyNT, byte[] serverChallenge, byte[] clientChallenge, long nanos1601, byte[] targetInfo)
  {
    int targetInfoLength = (targetInfo != null) ? targetInfo.length : 0;
    byte[] temp = new byte[28 + targetInfoLength + 4];

    Encdec.enc_uint32le(257, temp, 0);
    Encdec.enc_uint32le(0, temp, 4);
    Encdec.enc_uint64le(nanos1601, temp, 8);
    System.arraycopy(clientChallenge, 0, temp, 16, 8);
    Encdec.enc_uint32le(0, temp, 24);
    if (targetInfo != null)
      System.arraycopy(targetInfo, 0, temp, 28, targetInfoLength);
    Encdec.enc_uint32le(0, temp, 28 + targetInfoLength);

    return computeResponse(responseKeyNT, serverChallenge, temp, 0, temp.length);
  }

  public NtlmPasswordAuthentication(String userInfo)
  {
    this.domain = (this.username = this.password = null);

    if (userInfo != null) {
      try {
        userInfo = unescape(userInfo);
      }
      catch (UnsupportedEncodingException uee)
      {
      }

      int end = userInfo.length();
      int i = 0;
      int u = 0;
      for (i = 0; i < end; ++i) {
        char c = userInfo.charAt(i);
        if (c == ';') {
          this.domain = userInfo.substring(0, i);
          u = i + 1;
        } else if (c == ':') {
          this.password = userInfo.substring(i + 1);
          break;
        }
      }
      this.username = userInfo.substring(u, i);
    }

    initDefaults();

    if (this.domain == null) this.domain = DEFAULT_DOMAIN;
    if (this.username == null) this.username = DEFAULT_USERNAME;
    if (this.password == null) this.password = DEFAULT_PASSWORD;
  }

  public NtlmPasswordAuthentication(String domain, String username, String password)
  {
    if (username != null) {
      int ci = username.indexOf(64);
      if (ci > 0) {
        domain = username.substring(ci + 1);
        username = username.substring(0, ci);
      } else {
        ci = username.indexOf(92);
        if (ci > 0) {
          domain = username.substring(0, ci);
          username = username.substring(ci + 1);
        }
      }
    }

    this.domain = domain;
    this.username = username;
    this.password = password;

    initDefaults();

    if (domain == null) this.domain = DEFAULT_DOMAIN;
    if (username == null) this.username = DEFAULT_USERNAME;
    if (password == null) this.password = DEFAULT_PASSWORD;
  }

  public NtlmPasswordAuthentication(String domain, String username, byte[] challenge, byte[] ansiHash, byte[] unicodeHash)
  {
    if ((domain == null) || (username == null) || (ansiHash == null) || (unicodeHash == null))
    {
      throw new IllegalArgumentException("External credentials cannot be null");
    }
    this.domain = domain;
    this.username = username;
    this.password = null;
    this.challenge = challenge;
    this.ansiHash = ansiHash;
    this.unicodeHash = unicodeHash;
    this.hashesExternal = true;
  }

  public String getDomain()
  {
    return this.domain;
  }

  public String getUsername()
  {
    return this.username;
  }

  public String getPassword()
  {
    return this.password;
  }

  public String getName()
  {
    boolean d = (this.domain.length() > 0) && (!(this.domain.equals("?")));
    return ((d) ? this.domain + "\\" + this.username : this.username);
  }

  public byte[] getAnsiHash(byte[] challenge)
  {
    if (this.hashesExternal)
      return this.ansiHash;

    switch (LM_COMPATIBILITY)
    {
    case 0:
    case 1:
      return getPreNTLMResponse(this.password, challenge);
    case 2:
      return getNTLMResponse(this.password, challenge);
    case 3:
    case 4:
    case 5:
      if (this.clientChallenge == null) {
        this.clientChallenge = new byte[8];
        RANDOM.nextBytes(this.clientChallenge);
      }
      return getLMv2Response(this.domain, this.username, this.password, challenge, this.clientChallenge);
    }

    return getPreNTLMResponse(this.password, challenge);
  }

  public byte[] getUnicodeHash(byte[] challenge)
  {
    if (this.hashesExternal)
      return this.unicodeHash;

    switch (LM_COMPATIBILITY)
    {
    case 0:
    case 1:
    case 2:
      return getNTLMResponse(this.password, challenge);
    case 3:
    case 4:
    case 5:
      return new byte[0];
    }
    return getNTLMResponse(this.password, challenge);
  }

  public byte[] getSigningKey(byte[] challenge)
    throws jcifs.smb.SmbException
  {
    switch (LM_COMPATIBILITY)
    {
    case 0:
    case 1:
    case 2:
      byte[] signingKey = new byte[40];
      getUserSessionKey(challenge, signingKey, 0);
      System.arraycopy(getUnicodeHash(challenge), 0, signingKey, 16, 24);
      return signingKey;
    case 3:
    case 4:
    case 5:
      throw new jcifs.smb.SmbException("NTLMv2 requires extended security (jcifs.smb.client.useExtendedSecurity must be true if jcifs.smb.lmCompatibility >= 3)");
    }
    return null;
  }

  public byte[] getUserSessionKey(byte[] challenge)
  {
    if (this.hashesExternal) return null;
    byte[] key = new byte[16];
    try {
      getUserSessionKey(challenge, key, 0);
    } catch (Exception ex) {
      if (LogStream.level > 0)
        ex.printStackTrace(log);
    }
    return key;
  }

  void getUserSessionKey(byte[] challenge, byte[] dest, int offset)
    throws jcifs.smb.SmbException
  {
    MD4 md4;
    if (this.hashesExternal) return;
    try {
      md4 = new MD4();
      md4.update(this.password.getBytes("UTF-16LE"));
      switch (LM_COMPATIBILITY)
      {
      case 0:
      case 1:
      case 2:
        md4.update(md4.digest());
        md4.digest(dest, offset, 16);
        break;
      case 3:
      case 4:
      case 5:
        if (this.clientChallenge == null) {
          this.clientChallenge = new byte[8];
          RANDOM.nextBytes(this.clientChallenge);
        }

        HMACT64 hmac = new HMACT64(md4.digest());
        hmac.update(this.username.toUpperCase().getBytes("UTF-16LE"));

        hmac.update(this.domain.toUpperCase().getBytes("UTF-16LE"));

        byte[] ntlmv2Hash = hmac.digest();
        hmac = new HMACT64(ntlmv2Hash);
        hmac.update(challenge);
        hmac.update(this.clientChallenge);
        HMACT64 userKey = new HMACT64(ntlmv2Hash);
        userKey.update(hmac.digest());
        userKey.digest(dest, offset, 16);
        break;
      default:
        md4.update(md4.digest());
        md4.digest(dest, offset, 16);
      }
    }
    catch (Exception e) {
      throw new jcifs.smb.SmbException("", e);
    }
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof NtlmPasswordAuthentication));
    NtlmPasswordAuthentication ntlm = (NtlmPasswordAuthentication)obj;
    if ((!(ntlm.domain.toUpperCase().equals(this.domain.toUpperCase()))) || (!(ntlm.username.toUpperCase().equals(this.username.toUpperCase()))))
     {}
    if ((this.hashesExternal) && (ntlm.hashesExternal)) {
      return ((Arrays.equals(this.ansiHash, ntlm.ansiHash)) && (Arrays.equals(this.unicodeHash, ntlm.unicodeHash)));
    }

     return ((!(this.hashesExternal)) && (this.password.equals(ntlm.password)));
  }

  public int hashCode()
  {
    return getName().toUpperCase().hashCode();
  }

  public String toString()
  {
    return getName();
  }

  static String unescape(String str)
    throws NumberFormatException, UnsupportedEncodingException
  {
    int j;
    byte[] b = new byte[1];

    if (str == null) {
      return null;
    }

    int len = str.length();
    char[] out = new char[len];
    int state = 0;
    for (int i = j = 0; i < len; ++i)
      switch (state)
      {
      case 0:
        char ch = str.charAt(i);
        if (ch == '%')
          state = 1;
        else
          out[(j++)] = ch;

        break;
      case 1:
        b[0] = (byte)(Integer.parseInt(str.substring(i, i + 2), 16) & 0xFF);
        out[(j++)] = new String(b, 0, 1, "ASCII").charAt(0);
        ++i;
        state = 0;
      }


    return new String(out, 0, j);
  }
}