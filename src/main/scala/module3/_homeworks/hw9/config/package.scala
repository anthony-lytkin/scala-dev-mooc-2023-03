package module3.zio_homework

import zio.config.ReadError
import zio.config.ReadError.SourceError
import zio.config.typesafe.TypesafeConfig
import zio.{Cause, Has, IO, Layer, Task, ZIO, ZLayer}


package object config {

  case class ApiConfig(api: AppConfig) {
    override def toString: String = api.toString
  }

  case class AppConfig(host: String, port: String) {
    override def toString: String = s"App Config: http://$host:$port"
  }

  import zio.config.magnolia.DeriveConfigDescriptor.descriptor

  val apiConfigDescriptor = descriptor[ApiConfig]
  val configDescriptor = descriptor[AppConfig]

  type Configuration = zio.Has[AppConfig]
  type ApiConfiguration = zio.Has[ApiConfig]

  object ApiConfiguration {
    val live: Layer[ReadError[String], ApiConfiguration] = TypesafeConfig.fromDefaultLoader(apiConfigDescriptor)
  }

  object Configuration{
    val live: Layer[ReadError[String], Configuration] = TypesafeConfig.fromDefaultLoader(configDescriptor)
  }

  // Тут пришлось слегка похимичть, чтобы пропертис смог адекватно съесть,
  // не знаю можно ли так было делать в рамках задания
  def load: IO[ReadError[String], ApiConfig] = ZIO.accessM[ApiConfiguration](c => ZIO.effect(c.get)
    .orElseFail(SourceError("Can't get config from file"))).provideLayer(ApiConfiguration.live)
}