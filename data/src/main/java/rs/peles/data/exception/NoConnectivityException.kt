package rs.peles.data.exception

import java.io.IOException

class NoConnectivityException: IOException() {

    override fun getLocalizedMessage(): String {
        return "No Internet Connection"
    }
}