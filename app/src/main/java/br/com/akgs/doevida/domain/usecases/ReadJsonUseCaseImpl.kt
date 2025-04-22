package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.Cidade
import br.com.akgs.doevida.infra.Estado
import br.com.akgs.doevida.infra.ReadJson

class ReadJsonUseCaseImpl(
    private val readJson: ReadJson
) : ReadJsonUseCase {
    override fun readJsonCidades(): List<Cidade> {
        return readJson.readCidadesFromJson("cidades.json")
    }

    override fun readJsonEstados(): List<Estado> {
        val result = readJson.readEstadosFromJson("estados.json")
        return result
    }

    override fun getCidadesByEstado(siglaEstado: String): List<String> {
        val estados = readJson.readEstadosFromJson("estados.json")
        val estadoId = estados.firstOrNull { it.sigla == siglaEstado }?.id

        if (estadoId == null) {
            println("Estado não encontrado para a sigla: $siglaEstado")
            return emptyList()
        }

        // Filtra as cidades pelo ID do estado
        val cidades = readJsonCidades()
        return cidades.filter { it.estado == estadoId }.map { it.nome }

    }


}