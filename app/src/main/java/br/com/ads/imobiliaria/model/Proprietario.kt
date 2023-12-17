package br.com.ads.imobiliaria.model

/*
Os dados que devem ser armazenados proprietários são:
a) CPF_prop
b) Nome
c) E-mail
* */

open class Proprietario(
    cpf: String,
    nome: String,
    email: String,
    imovel: String
){
    var cpf: String
    var nome: String
    var email: String
    var imovel: String

    init {
        this.cpf = cpf
        this.nome = nome
        this.email = email
        this.imovel = imovel
    }

    override fun toString(): String {
        return "CPF: " +this.cpf +
                "\nNome: " +this.nome +
                "\nEmail: " + this.email +
                "\nImovel: " +this.imovel + "" +
                "\n------------------------------------------------------"
    }
}