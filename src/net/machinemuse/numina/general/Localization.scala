package net.machinemuse.numina.general

import net.minecraft.util.{StatCollector, StringTranslate}
import java.io._
import java.util.Properties
import cpw.mods.fml.common.registry.LanguageRegistry
import net.minecraft.client.Minecraft
import com.google.common.base.Charsets
import net.machinemuse.numina.general.MuseLogger

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:34 PM, 6/28/13
 */
object Localization {
  val LANG_PATH = "/assets/powersuits/lang/"
  var extractedLanguage = ""

  def getCurrentLanguage = Minecraft.getMinecraft.getLanguageManager.getCurrentLanguage.getLanguageCode

  def loadCurrentLanguage() {
    if (getCurrentLanguage != extractedLanguage) {
      extractedLanguage = getCurrentLanguage
      try {
        val inputStream: InputStream = this.getClass.getResourceAsStream(LANG_PATH + extractedLanguage + ".lang")
        val langPack: Properties = new Properties
        langPack.load(new InputStreamReader(inputStream, Charsets.UTF_8))
        LanguageRegistry.instance.addStringLocalization(langPack, extractedLanguage)
      } catch {
        case e: Exception => {
          e.printStackTrace()
          MuseLogger.logError("Couldn't read MPS localizations for language " + extractedLanguage + " :(")
        }
      }
    }
  }

  def translate(str: String) = {
//    loadCurrentLanguage()
    StatCollector.translateToLocal(str)
  }
}
