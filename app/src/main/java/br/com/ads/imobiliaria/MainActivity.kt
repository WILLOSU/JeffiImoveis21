package br.com.ads.imobiliaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.ads.imobiliaria.banco.dao.ImovelDAO
import br.com.ads.imobiliaria.banco.dao.InquilinoDAO
import br.com.ads.imobiliaria.banco.dao.ProprietarioDAO
import br.com.ads.imobiliaria.navigation.AppDestination
import br.com.ads.imobiliaria.ui.theme.ImobiliariaTheme
import br.com.ads.imobiliaria.ui.theme.screens.ImovelScreen
import br.com.ads.imobiliaria.ui.theme.screens.InquilinoScreen
import br.com.ads.imobiliaria.ui.theme.screens.PrincipalScreen
import br.com.ads.imobiliaria.ui.theme.screens.ProprietarioScreen
import br.com.ads.imobiliaria.ui.theme.screens.SalvarScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImobiliariaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val proprietarioDAO = ProprietarioDAO(this)
                    val imovelDAO = ImovelDAO(this)
                    val inquilinoDAO = InquilinoDAO(this)

                    MyApp(proprietarioDAO, imovelDAO, inquilinoDAO)
                }
            }
        }
    }
}

@Composable
fun MyApp(proprietarioDAO: ProprietarioDAO, imovelDAO: ImovelDAO, inquilinoDAO: InquilinoDAO) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestination.Principal.route
    ) {
        composable(AppDestination.Principal.route) {
            PrincipalScreen(navController)
        }
        composable(AppDestination.Proprietario.route) {
            ProprietarioScreen(proprietarioDAO = proprietarioDAO, imovelDAO = imovelDAO, navController = navController)
        }
        composable(AppDestination.Inqulino.route) {
            InquilinoScreen(inquilinoDAO = inquilinoDAO, imovelDAO = imovelDAO, navController = navController)
        }
        composable(AppDestination.Imovel.route) {
            ImovelScreen(imovelDAO = imovelDAO, navController = navController)
        }
        composable(AppDestination.Salvar.route) {
            SalvarScreen(proprietarioDAO = proprietarioDAO, imovelDAO = imovelDAO, inquilinoDAO = inquilinoDAO, navController = navController)
        }
    }
}
