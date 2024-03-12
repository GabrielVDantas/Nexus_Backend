package br.com.nexus.Nexus.entity.project;

public enum Category {
    ACAO("Ação"),
    ARCADE("Arcade"),
    AVENTURA ("Aventura"),
    CARTAS("Cartas"),
    CASSINO("Cassino"),
    CASUAL("Casual"),
    CORRIDA("Corrida"),
    CURIOSIDADES("Curiosidades"),
    EDUCATIVO("Educativo"),
    ESPORTES("Esportes"),
    ESTRATEGIA("Estratégia"),
    MUSICA("Música"),
    PALAVRAS("Palavras"),
    QUEBRA_CABECA("Quebra cabeça"),
    RPG("RPG"),
    SIMULACAO("Simulação"),
    TABULEIRO("Tabuleiro");

    private String category;

    Category(String category) {
        this.category = category;
    }
}
