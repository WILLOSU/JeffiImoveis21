package br.com.ads.imobiliaria.model

/*
Os dados que devem ser armazenados dos inquilinos são:
a) CPF_inq
b) Nome
c) Valor_caução_depositado (Float)
*/

open class Inquilino(
    cpf: String,
    nome: String,
    valorCaucaoDepositado: Float,
    imovel: String
){
    var cpf: String
    var nome: String
    var valorCaucaoDepositado: Float
    var imovel: String

    init {
        this.cpf = cpf
        this.nome = nome
        this.valorCaucaoDepositado = valorCaucaoDepositado
        this.imovel = imovel
    }

    override fun toString(): String {
        return "CPF: " +this.cpf +
                "\nNome: " +this.nome +
                "\nValor Caução: " + this.valorCaucaoDepositado +
                "\nImovel: " +this.imovel + "" +
                "\n------------------------------------------------------"

    }
}