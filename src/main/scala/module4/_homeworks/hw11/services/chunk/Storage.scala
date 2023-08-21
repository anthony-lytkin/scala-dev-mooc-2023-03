package module4._homeworks.hw11.services.chunk

import java.nio.file.{Path, Paths}

object Storage {

  private val STORAGE_PATH = System.getProperty("user.dir") + "/src/main/scala/module4/_homeworks/hw11/services/chunk/storage/"
  private val STORAGE_NAME = "storage.txt"

  val PATH: Path = Paths.get(STORAGE_PATH + STORAGE_NAME)

}
