package codesniffer.codefunnel

import javax.sql.DataSource

import com.caibowen.gplume.resource.ClassLoaderInputStreamProvider
import gplume.scala.context.{AppContext, ContextBooter}
import gplume.scala.jdbc.DB
import org.postgresql.jdbc.PgArray
import org.postgresql.util.PGobject

/**
  * Created by Bowen Cai on 2/24/2016.
  */
class DBSupport {
  val manifestPath = "assemble.xml"
  val classLoader = getClass.getClassLoader

  private var booted = false
  def boot() {
    if (!booted) {
      val _bootstrap = new ContextBooter
      _bootstrap.setClassLoader(classLoader)
      _bootstrap.setStreamProvider(new ClassLoaderInputStreamProvider(classLoader))
      _bootstrap.setManifestPath(manifestPath)
      _bootstrap.boot()
      booted = true
    }
  }

  lazy val datasource :DataSource = AppContext.beanAssembler.getBean("dataSource")
  lazy val db = new DB(datasource)
}
