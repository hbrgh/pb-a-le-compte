package montresor.condeminov.utils;

/**
 * helper class to check the operating system this Java VM runs in
 *
 * please keep the notes below as a pseudo-license
 *
 * http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
 * compare to http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java
 * http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html
 */
import java.util.Locale;
public final  class OsCheckUtils {
  /**
   * types of Operating Systems
   */
  public enum OSType {
    WINDOWS, OSX, LINUX, OTHER_OS
  }

  // cached result of OS detection
  protected static OSType detectedOperatingSystem;

  /**
   * detect the operating system from the os.name System property and cache
   * the result
   * 
   * @returns - the operating system detected
   */
  public static OSType getOperatingSystemType() {
    if (detectedOperatingSystem == null) {
      String operatingSystem = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
      if ((operatingSystem.indexOf("mac") >= 0) || (operatingSystem.indexOf("darwin") >= 0)) {
        detectedOperatingSystem = OSType.OSX;
      } else if (operatingSystem.indexOf("win") >= 0) {
        detectedOperatingSystem = OSType.WINDOWS;
      } else if (operatingSystem.indexOf("nux") >= 0) {
        detectedOperatingSystem = OSType.LINUX;
      } else {
        detectedOperatingSystem = OSType.OTHER_OS;
      }
    }
    return detectedOperatingSystem;
  }
}