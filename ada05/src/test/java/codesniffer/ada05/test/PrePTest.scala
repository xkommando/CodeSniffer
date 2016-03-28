package codesniffer.ada05.test

import java.io.{File, FileInputStream}

import codesniffer.ada05.{PreprocessContext, AdaPreprocessor}
import org.apache.commons.io.FileUtils
import org.junit.Test


class PrePTest {

  @Test
  def t1() {

    val codePath = "E:\\trans\\myada\\small.ada"
    //    val codePath = "E:\\\\trans\\\\ada\\\\xmlada-4.4w-src\\\\unicode\\\\unicode-names-high_private_surrogates.ads"
    val encoding = "UTF-8"
    val wk = new AdaPreprocessor()
    val nSrc = wk.preprocess(new FileInputStream(codePath), new PreprocessContext)

    val f = new File("D:\\t.ada")
    if (!f.exists()) {
      f.createNewFile()
    }
    val newSrc = nSrc.toString()
    //    println()
    //    println(newSrc)
    FileUtils.writeStringToFile(f, newSrc, encoding)
  }


}