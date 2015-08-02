package codesniffer.ada05.test

import java.io.{File, FileInputStream}

import codesniffer.ada05.{AdaPreprocessor}
import org.junit.Test


class PrePTest {

  @Test
  def t1() {

    val codePath = "E:\\trans\\myada\\small.ada"
    //    val codePath = "E:\\\\trans\\\\ada\\\\xmlada-4.4w-src\\\\unicode\\\\unicode-names-high_private_surrogates.ads"
    val encoding = "UTF-8"
    val wk = new AdaPreprocessor()
    wk.preprocess(new File(codePath))

  }


}