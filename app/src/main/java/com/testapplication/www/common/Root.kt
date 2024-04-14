package com.testapplication.www.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.homescreen.create.CreateScreen
import com.testapplication.www.homescreen.followupcalls.FollowupCallsScreen
import com.testapplication.www.homescreen.home.HomeScreen
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
    Create

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
                toHomeScreen = { navController.navigate(Screens.Home.name) },
                modifier = Modifier
            )
        }
        composable(Screens.SignUp.name) {
            SignupScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name) },
                context
            )
        }
        composable(Screens.Login.name) {
            LoginScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name) },
                context
            )
        }
        composable(Screens.Home.name) {
           HomeScreen(
               toOnboarding = { navController.navigate(Screens.Onboarding.name) },
               toScheduledVisits = { navController.navigate(Screens.Scheduledvisits.name) },
               toFollowupCalls = { navController.navigate(Screens.FollowupCalls.name) },
               toLeadsScreen = { navController.navigate(Screens.Leads.name) },
               context,
               toCreate = {navController.navigate(Screens.Create.name)}
           )

        }
        composable(Screens.Scheduledvisits.name){
            ScheduledVisitsScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name)},
                toFollowupCalls = { navController.navigate(Screens.FollowupCalls.name) },
                toLeadsScreen = { navController.navigate(Screens.Leads.name) },
                context
            )
        }
        composable(Screens.FollowupCalls.name){
            FollowupCallsScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name)},
                toLeadsScreen = { navController.navigate(Screens.Leads.name) },
                toScheduledVisits = { navController.navigate(Screens.Scheduledvisits.name) },
                context
            )
        }
        composable(Screens.Leads.name){
            LeadScreen(
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name)},
                toFollowupCalls = { navController.navigate(Screens.FollowupCalls.name) },
                toScheduledVisits = { navController.navigate(Screens.Scheduledvisits.name) },
                context
            )
        }
        composable(Screens.BottomNavigation.name){
            BottomBar(
                currentScreen = "Leads",
                toOnboarding = { navController.navigate(Screens.Onboarding.name) },
                toHome = { navController.navigate(Screens.Home.name)},
                toScheduledVisits = { navController.navigate(Screens.Scheduledvisits.name) },
                toFollowupCalls = { navController.navigate(Screens.FollowupCalls.name) },
                toLeadsScreen = { navController.navigate(Screens.Leads.name) }

            ) {

            }
        }
        composable(Screens.Create.name){
            CreateScreen(toHome = { navController.navigate(Screens.Home.name) }, context )
        }


    }
}

