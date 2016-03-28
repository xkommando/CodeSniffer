package codesniffer.codefunnel.utils

import javax.sql.DataSource

import com.caibowen.gplume.resource.ClassLoaderInputStreamProvider
import gplume.scala.context.{AppContext}
import gplume.scala.jdbc.DB

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object DBUtils {

  def quote(param: String): String = {
    val b = new scala.StringBuilder(param.length * 3 / 2)
    b.append(''')

    val _idx1 = param.indexOf(''')
    if (_idx1 < 0)
      b.append(param)
    else {
      for (c <- param) c match {
        case ''' => b append ''' append '''
        case o => b append o
      }
    }
    b.append(''').toString()
  }

  lazy val datasource :DataSource = AppContext.beanAssembler.getBean("dataSource")
  lazy val db = new DB(datasource)
}
