package client

import javax.inject.{Inject, Singleton}

import play.api.Configuration
import slick.dbio.DBIOAction
import slick.jdbc.JdbcBackend._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MySqlClient @Inject()(configuration: Configuration) {
  lazy val user = configuration.getString("MYSQL_USERNAME").get
  lazy val password = configuration.getString("MYSQL_PASSWORD").get
  lazy val url = configuration.getString("MYSQL_URL").get
  lazy val sqlDriver = configuration.getString("MYSQL_DRIVER").get

  lazy val connection: DatabaseDef = Database.forURL(url, user, password, driver = sqlDriver)

  def execute[R](query: DBIOAction[R, slick.dbio.NoStream, scala.Nothing]) = {
    connection.run(query) recover {
      case ex: Exception => throw new Exception(ex)
    }
  }
}