package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.Cidade
import br.com.akgs.doevida.infra.Estado

interface ReadJsonUseCase {
    fun readJsonCidades(): List<Cidade>
    fun readJsonEstados(): List<Estado>
    fun getCidadesByEstado(estado: String): List<String>
}