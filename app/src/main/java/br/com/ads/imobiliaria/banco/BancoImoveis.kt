package br.com.ads.imobiliaria.banco

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoImoveis(context: Context) : SQLiteOpenHelper(context, "Banco", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        criarTabelaImovel(db)
        criarTabelaProprietario(db)
        criarTabelaInquilino(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        val SQLExclusaoImovel = "DROP TABLE IF EXISTS Imovel"
        db.execSQL(SQLExclusaoImovel)
        val SQLExclusaoProp = "DROP TABLE IF EXISTS Proprietario"
        db.execSQL(SQLExclusaoProp)
        val SQLExclusaoInquilino = "DROP TABLE IF EXISTS Inquilino"
        db.execSQL(SQLExclusaoInquilino)
        onCreate(db)
    }

    private fun criarTabelaImovel(db: SQLiteDatabase) {
        val nomeTabela = "Imovel"
        val matricula = "matricula"
        val endereco = "endereco"
        val valorAluguel = "valor_aluguel"

        val SQL_criacao =
            "CREATE TABLE $nomeTabela (" +
                    "$matricula TEXT PRIMARY KEY," +
                    "$endereco TEXT," +
                    "$valorAluguel REAL)"

        db.execSQL(SQL_criacao)
    }

    private fun criarTabelaProprietario(db: SQLiteDatabase) {
        val nomeTabela = "Proprietario"
        val cpf = "cpf"
        val nome = "nome"
        val email = "email"
        val imovel = "imovel"

        val SQL_criacao =
            "CREATE TABLE $nomeTabela (" +
                    "$cpf TEXT PRIMARY KEY," +
                    "$nome TEXT," +
                    "$email TEXT," +
                    "$imovel TEXT)"

        db.execSQL(SQL_criacao)
    }

    private fun criarTabelaInquilino(db: SQLiteDatabase) {
        val nomeTabela = "Inquilino"
        val cpf = "cpf"
        val nome = "nome"
        val imovel = "imovel"
        val valorCaucaoDepositado = "valor_caucao_depositado"

        val SQL_criacao =
            "CREATE TABLE $nomeTabela (" +
                    "$cpf TEXT PRIMARY KEY," +
                    "$nome TEXT," +
                    "$imovel TEXT," +
                    "$valorCaucaoDepositado REAL)"

        db.execSQL(SQL_criacao)
    }


}