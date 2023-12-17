package br.com.ads.imobiliaria.banco.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import br.com.ads.imobiliaria.banco.BancoImoveis
import br.com.ads.imobiliaria.model.Proprietario
import java.io.File
import java.io.FileOutputStream

class ProprietarioDAO(context: Context) {

    private val meuBanco: SQLiteDatabase = BancoImoveis(context).writableDatabase

    fun inserir(proprietario: Proprietario) {
        val valores = ContentValues()
        valores.put("cpf", proprietario.cpf)
        valores.put("nome", proprietario.nome)
        valores.put("email", proprietario.email)
        valores.put("imovel", proprietario.imovel)

        meuBanco.insert("Proprietario", null, valores)
    }

    fun obterTodos(): List<Proprietario> {
        val proprietarios = mutableListOf<Proprietario>()
        val cursor: Cursor = meuBanco.rawQuery("SELECT * FROM Proprietario", null)

        while (cursor.moveToNext()) {
            val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val imovel = cursor.getString(cursor.getColumnIndexOrThrow("imovel"))

            proprietarios.add(Proprietario(cpf, nome, email, imovel))
        }
        cursor.close()
        return proprietarios
    }

    fun atualizar(proprietario: Proprietario) {
        val valores = ContentValues()
        valores.put("nome", proprietario.nome)
        valores.put("email", proprietario.email)
        valores.put("imovel", proprietario.imovel)

        meuBanco.update("Proprietario", valores, "cpf = ?", arrayOf(proprietario.cpf))
    }

    fun excluir(cpf: String) {
        meuBanco.delete("Proprietario", "cpf = ?", arrayOf(cpf))
    }

    fun salvarArquivosProprietarios(): String{
        val dados = StringBuilder()
        val cursor: Cursor = meuBanco.rawQuery("SELECT * FROM Proprietario", null)

        while (cursor.moveToNext()) {
            val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val imovel = cursor.getString(cursor.getColumnIndexOrThrow("imovel"))
            val proprietario = Proprietario(cpf, nome, email, imovel)
            dados.append("${proprietario}\n")
        }
        cursor.close()

        return dados.toString()
    }
}
