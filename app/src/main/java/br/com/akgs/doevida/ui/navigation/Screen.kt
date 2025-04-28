package br.com.akgs.doevida.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")

    object Home : Screen("home")
    object RequestDonation : Screen("request_donation")
    object Profile : Screen("profile")

    object Solicitation : Screen("solicitation")

    object MyDonation : Screen("my_donation")
    object Information : Screen("information")

    companion object {
        val bottomBar = listOf(
            Home,
            RequestDonation,
            Profile
        )

        val noBottomBar = listOf(
            Login,
            Register,
            Solicitation,
            MyDonation,
            Information
        )
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            if (args.isNotEmpty()) {
                append("?")
                args.forEachIndexed { index, arg ->
                    append("$arg")
                    if (index < args.size - 1) {
                        append("&")
                    }
                }
            }
        }
    }
}