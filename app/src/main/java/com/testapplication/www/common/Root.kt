package com.testapplication.www.common

import CreateScreen
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.homescreen.followupcalls.FollowupCallsScreen
import com.testapplication.www.homescreen.home.HomeScreen
import com.testapplication.www.homescreen.home.displayList
import com.testapplication.www.homescreen.leads.LeadScreen
import com.testapplication.www.homescreen.scheduledvisits.ScheduledVisitsScreen
import com.testapplication.www.loginscreen.LoginScreen
import com.testapplication.www.onboardingscreen.OnboardingScreen
import com.testapplication.www.signupscreen.SignupScreen

enum class Screens {
    Onboarding,
    Login,
    SignUp,
    Home,
    Scheduledvisits,
    FollowupCalls,
    Leads,
    BottomNavigation,
    Create,
    DisplayList

}

@Composable
fun Root(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Onboarding.name,

        ) {

        composable(Screens.Onboarding.name) {
            OnboardingScreen(
                toLoginScreen = { navController.navigate(Screens.Login.name) },
                toSignupScreen = { navController.navigate(Screens.SignUp.name) },
                toHomeScreen = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                modifier = Modifier,
                context
            )
        }
        composable(Screens.SignUp.name) {
            SignupScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                context
            )
        }
        composable(Screens.Login.name) {
            LoginScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                context
            )
        }
        composable("${Screens.Home.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            HomeScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toScheduledVisits = { userId -> navController.navigate("${Screens.Scheduledvisits.name}/$userId") },
                toFollowupCalls = { userId -> navController.navigate("${Screens.FollowupCalls.name}/$userId") },
                toLeadsScreen = { userId -> navController.navigate("${Screens.Leads.name}/$userId") },
                userID = userId,
                context,
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}?userId=$userId,itemId=$itemId")
                }
            )

        }
        composable("${Screens.Scheduledvisits.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            ScheduledVisitsScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                toFollowupCalls = { userId -> navController.navigate("${Screens.FollowupCalls.name}/$userId") },
                toLeadsScreen = { userId -> navController.navigate("${Screens.Leads.name}/$userId") },
                context,
                userID = userId,
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}?userId=$userId,itemId=$itemId")
                }
            )
        }
        composable("${Screens.FollowupCalls.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            FollowupCallsScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                toLeadsScreen = { userId -> navController.navigate("${Screens.Leads.name}/$userId") },
                toScheduledVisits = { userId -> navController.navigate("${Screens.Scheduledvisits.name}/$userId") },
                context, userID = userId,
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}?userId=$userId,itemId=$itemId")
                }

            )
        }
        composable("${Screens.Leads.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            LeadScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                toFollowupCalls = { userId -> navController.navigate("${Screens.FollowupCalls.name}/$userId") },
                toScheduledVisits = { userId -> navController.navigate("${Screens.Scheduledvisits.name}/$userId") },
                userID = userId,
                context,
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}?userId=$userId,itemId=$itemId")
                }
            )
        }
        composable(Screens.BottomNavigation.name) {
            BottomBar(
                currentScreen = "Leads",
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name) },
                toScheduledVisits = { navController.navigate(Screens.Scheduledvisits.name) },
                toFollowupCalls = { navController.navigate(Screens.FollowupCalls.name) },
                toLeadsScreen = { navController.navigate(Screens.Leads.name) }

            ) {

            }
        }
        composable("${Screens.Create.name}?userId={userId},itemId={itemId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                },
                navArgument("itemId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            val itemId = backStackEntry.arguments?.getLong("itemId", 0L) ?: 0L
            CreateScreen(
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                context,
                userID = userId,
                itemId

            )
        }

        composable("${Screens.DisplayList.name}/{userId}/{itemId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            val itemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull() ?: 0L
            displayList(
                context = context,
                userId = userId,
                "",
                valueType = "",
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}/$userId/$itemId")
                }
            )
        }


    }
}

