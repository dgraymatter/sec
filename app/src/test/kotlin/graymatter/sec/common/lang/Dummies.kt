package graymatter.sec.common.lang

object DummyObject

sealed class SealedDummy {

    object Singleton : SealedDummy()

    class CaseKitty : SealedDummy() {
        lateinit var kitty: Any
    }

}
