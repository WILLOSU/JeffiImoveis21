package br.com.ads.imobiliaria.navigation

sealed class AppDestination(val route: String) {
    object Principal : AppDestination("Principal")
    object Imovel : AppDestination("Imovel")
    object Proprietario : AppDestination("Proprietário")
    object Inqulino : AppDestination("Inquilino")
    object Salvar : AppDestination("Salvar")
}
