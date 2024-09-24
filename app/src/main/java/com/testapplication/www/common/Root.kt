package com.testapplication.www.common

import CheckInScreen
import CreateScreen
import CreationLedgerScreen
import LedgerViewForm
import android.content.Context
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
import com.testapplication.www.homescreen.home.DisplayList
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
    DisplayList,
    CheckIn,
    CreationLedger,
    LedgerViewForm

}

@Composable
fun Root(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Onboarding.name,

        ) {

        composable(Screens.Onboarding.name) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            OnboardingScreen(
                toLoginScreen = { navController.navigate(Screens.Login.name) },
                toSignupScreen = { navController.navigate(Screens.SignUp.name) },
                toHomeScreen = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                modifier = Modifier,
                context
            )
        }
        composable(Screens.SignUp.name) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            SignupScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                context
            )
        }
        composable(Screens.Login.name) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
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
                    navController.navigate("${Screens.Create.name}?userId=$userId&itemId=$itemId")
                },
                toCheckIn = { userId -> navController.navigate("${Screens.CheckIn.name}/$userId") },
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")
                },


                )

        }
        composable("${Screens.Scheduledvisits.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            ScheduledVisitsScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { userId -> navController.navigate("${Screens.Home.name}/$userId") },
                toFollowupCalls = { userId -> navController.navigate("${Screens.FollowupCalls.name}/$userId") },
                toLeadsScreen = { userId -> navController.navigate("${Screens.Leads.name}/$userId") },
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")
                },
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
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")
                },
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
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")
                },

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
        composable("${Screens.Create.name}?userId={userId}&itemId={itemId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.LongType },
                navArgument("itemId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: 0L
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
            DisplayList(
                context = context,
                userId = userId,
                itemId = itemId,
                "",
                valueType = "",
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}/$userId/$itemId")
                },
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")

                },
            )
        }

        composable("${Screens.CheckIn.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L

            CheckInScreen(
                toHome = { navController.popBackStack() },
                userID = userId,
                context = context
            )
        }

        composable("${Screens.CreationLedger.name}/{userId}/{itemId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
            val itemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull() ?: 0L
            val ledgerItemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull() ?: 0L
            CreationLedgerScreen(
                toHome = { navController.popBackStack() },
                toCreate = { userId, itemId ->
                    navController.navigate("${Screens.Create.name}?userId=$userId&itemId=$itemId")
                },
                toCreationLedger = { userId, itemId ->
                    navController.navigate("${Screens.CreationLedger.name}/$userId/$itemId")

                },
                toLedgerViewForm = { ledgerItemId ->
                    navController.navigate("${Screens.LedgerViewForm.name}/$ledgerItemId")
                }, userID = userId,
                itemID = itemId,
                context = context
            )
        }

        composable("${Screens.LedgerViewForm.name}/{ledgerItemId}") { backStackEntry ->
            val ledgerItemId =
                backStackEntry.arguments?.getString("ledgerItemId")?.toLongOrNull() ?: 0L
            LedgerViewForm(ledgerItemId = ledgerItemId, context = context)
        }


    }
}

