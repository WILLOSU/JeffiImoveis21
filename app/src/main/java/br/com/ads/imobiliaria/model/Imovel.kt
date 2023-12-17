package br.com.ads.imobiliaria.model


/*
Os dados que devem ser armazenados do imóvel são:
a) Matrícula (String)
b) Endereço (String)
c) Valor_do_aluguel (Float)
 */

open class Imovel constructor(
    matricula: String,
    endereco: String,
    valoraluguel: Float
){
    var matricula : String
    var endereco : String
    var valoraluguel : Float

    init {
        this.matricula = matricula
        this.endereco = endereco
        this.valoraluguel = valoraluguel
    }

    override fun toString(): String {
        return "Matricula: " +this.matricula +
                "\nEndereço: " +this.endereco +
                "\nValor Aluguel: " + this.valoraluguel + "" +
                "\n------------------------------------------------------"
    }
}


