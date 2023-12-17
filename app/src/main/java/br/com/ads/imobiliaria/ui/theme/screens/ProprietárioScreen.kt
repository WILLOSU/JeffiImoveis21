package br.com.ads.imobiliaria.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.ads.imobiliaria.banco.dao.ImovelDAO
import br.com.ads.imobiliaria.banco.dao.ProprietarioDAO
import br.com.ads.imobiliaria.model.Proprietario
import br.com.ads.imobiliaria.ui.theme.componentes.ImagemCardComponent
import br.com.ads.imobiliaria.ui.theme.componentes.MenuImoveisComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoBoldComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TopBarComponent
import br.com.ads.imobiliaria.ui.theme.componentes.EditTextComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoComponent

@Composable
fun ProprietarioScreen(
    proprietarioDAO: ProprietarioDAO,
    navController: NavHostController,
    imovelDAO: ImovelDAO
) {
    Scaffold(
        topBar = {
            TopBarComponent("Proprietário", navController)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary),
//                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var proprietarios by remember { mutableStateOf(listOf<Proprietario>()) }
            proprietarios = proprietarioDAO.obterTodos()
            var selectedCpf by remember{mutableStateOf("")}
            var selectedMatricula by remember{mutableStateOf("")}
            val listState = rememberLazyListState()

            LazyColumn(state = listState,
                modifier = Modifier.heightIn(min = 250.dp, max = 250.dp)
            ) {
                items(proprietarios) { proprietario ->
                    BlocoProprietario(proprietario, selectedCpf){ newSelectedCpf ->
                        selectedCpf = newSelectedCpf
                    }
                }
            }

            var isInputEmpty by rememberSaveable { mutableStateOf(false) }
            val cpf = EditTextComponent("CPF", isInputEmpty)
            val nome = EditTextComponent("Nome", isInputEmpty)
            val email = EditTextComponent("Email", isInputEmpty)

            val imoveis = imovelDAO.obterTodos()
            MenuImoveisComponent(imoveis){ newSelectedImovel ->
                selectedMatricula = newSelectedImovel
            }
            Button(
                onClick = {
                    if(nome.isNotEmpty() && cpf.isNotEmpty()){
                        val newProprietario = Proprietario(cpf, nome, email, selectedMatricula)
                        proprietarioDAO.inserir(newProprietario)
                    }else{
                        isInputEmpty = true
                    }
                    proprietarios = proprietarioDAO.obterTodos()
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Inserir")
            }
            Button(
                onClick = {
                    if(selectedCpf.isNotEmpty()){
                        proprietarioDAO.excluir(selectedCpf)
                        proprietarios = proprietarioDAO.obterTodos()
                    }
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Deletar")
            }
            Button(
                onClick = {
                    if(nome.isNotEmpty() && email.isNotEmpty()){
                        val newProprietario = Proprietario(selectedCpf, nome, email, selectedMatricula)
                        proprietarioDAO.atualizar(newProprietario)
                    }else{
                        isInputEmpty = true
                    }
                    proprietarios = proprietarioDAO.obterTodos()
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Editar")
            }
        }
    }
}

@Composable
fun BlocoProprietario(proprietario: Proprietario, selectedCpf: String, onSelectedCpfChange: (String) -> Unit){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .selectable(
                selected = proprietario.cpf == selectedCpf,
                onClick = {
                    if (selectedCpf != proprietario.cpf) {
                        onSelectedCpfChange(proprietario.cpf)
                    } else {
                        onSelectedCpfChange("")
                    }
                }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(2.dp, color = Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if(proprietario.cpf == selectedCpf) {
                MaterialTheme.colorScheme.tertiary
            }else{
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagemCardComponent()
            Column {
                TextoBoldComponent("CPF: " + proprietario.cpf)
                TextoBoldComponent("Nome: " + proprietario.nome)
                TextoBoldComponent("Email: " + proprietario.email)
                TextoBoldComponent("Matricula Imovel: " + proprietario.imovel)
            }
        }
    }

}

