object Product {
    const val version = "0.0.1-SNAPSHOT"
    const val group = "graymatter.sec"
}


allprojects {
    version = Product.version
    group = Product.group
}
