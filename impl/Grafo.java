package impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Classe de implementação do Grafo
 */
public class Grafo {
    /**
     * Os vertices representam o usuário
     */
    private List<Vertice> vertices;

    /**
     * As arestas representam uma amizade entre usuários
     */
    private List<Aresta> arestas;

    public Grafo(){
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

     /**
     * Método que busca e verifica a existência de um vértice
     * @param valor Nome do usuário a ser buscado
     * @return Retorna o vértice encontrado se exitir e null se não for encontrado
     */
    public Vertice verificaVertice(String valor){
        for (Vertice vertice : vertices) {
            if(vertice.getValor().equalsIgnoreCase(valor)){
                return vertice;
            }
        }
        return null;
    }

    /**
     * Método que verifica se dois vertices estão conectados (se há uma amizade entre eles)
     * @param usuario1 Usuario 1
     * @param usuario2 Usuario 2
     * @return True se existe, False se não existe
     */
    public boolean haAresta(Vertice usuario1, Vertice usuario2 ){
        for (Aresta aresta : arestas){
            if (aresta.getOrigem().equals(usuario1) && aresta.getDestino().equals(usuario2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que adiciona um "usuário" ao grafo
     * @param valor Nome do usuário
     */
    public void adicionarVertice(String valor){
        if(verificaVertice(valor) == null){
            this.vertices.add(new Vertice(valor));
        }
    }

    /**
     * Método que cria uma amizade entre o usuário 1 e o usuário 2
     * Pelo grafo ser não direcional, é necessário adicionar uma "amizade" (ou aresta) nos dois sentidos (Ex: 1-2 e 2-1)
     * @param valor Nome do usuário
     */
    public void adicionarAresta(String nomeUsuario1, String nomeUsuario2){
        Vertice usuario1 = verificaVertice(nomeUsuario1);
        Vertice usuario2 = verificaVertice(nomeUsuario2);

        if(!haAresta(usuario1, usuario2)){
            arestas.add(new Aresta(usuario1, usuario2));
            arestas.add(new Aresta(usuario2, usuario1));
        }
    }

    /**
     * Método que conta quantos amigos um usuário tem
     * @param usuario Usuario para verificação
     * @return O número de amigos do usuário
     */
    public Integer contarAmigos(Vertice usuario){
        Integer count = 0;
        for (Aresta aresta : arestas) {
            if(aresta.getOrigem().equals(usuario)){
                count++;
            }
        }
        return count;
    }

    /**
     * Método para listar os amigos de um usuário. O método verifica se o usuário é a origem de alguma aresta (ou amizade)
     * e armazena o destino da aresta (o usuario amigo) em uma lista
     * @param usuario Usuario para buscar os amigos
     * @return Uma lista de vertices (usuarios) que são amigos do usuário que foi passado como parâmetro
     */
    public List<Vertice> listarAmigosDeUmUsuario(Vertice usuario){
        List<Vertice> amigos = new ArrayList<>();

        for (Aresta aresta : arestas) {
            if(aresta.getOrigem().equals(usuario)){
                amigos.add(aresta.getDestino());
            }
        }

        return amigos;
    }
    /**
     * Método que busca o usuário mais influente
     * @return Nome do usuário mais influnte caso haja um e "Nenhum usuário é influente" caso não haja
     */
    public String buscaNomeUsuarioMaisInfluente(){
        int max = 0;
        Vertice usuarioMaisInfluente = null;

        for (Vertice vertice : vertices){
            Integer numeroDeAmigos = contarAmigos(vertice);
            if(numeroDeAmigos > max){
                max = numeroDeAmigos;
                usuarioMaisInfluente = vertice;
            }
        }

        return usuarioMaisInfluente != null ? usuarioMaisInfluente.getValor() + " (" + max + " amigos)" : "Nenhum usuário é influente";
    }
   
    /**
     * Método que busca se um usuário A tem alguma conexão com um usuário B (Verifica se o vertice A tem algum caminho pro vertice B)
     * Utiliza a busca por largura
     * @param nomeUsuario1 Nome do usuário 1
     * @param nomeUsuario2 Nome do usuário 2
     * @return True se existe, False se não existe
     */
    public Boolean existeAlgumCaminhoEntreOsUsuarios(String nomeUsuario1, String nomeUsuario2){
        
        Vertice verticeInicio = verificaVertice(nomeUsuario1);
        Vertice verticeFim = verificaVertice(nomeUsuario2);

        if(Objects.isNull(verticeInicio) || Objects.isNull(verticeFim)){return false;}

         ArrayList<Vertice> visitados = new ArrayList<>();
        Queue<Vertice> fila = new LinkedList<>();
        fila.add(verticeInicio);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            if (atual == verticeFim) return true;
            if (!visitados.contains(atual)) {
                visitados.add(atual);
                for (Vertice vizinho : listarAmigosDeUmUsuario(atual)) {
                    if (!visitados.contains(vizinho)) {
                        fila.add(vizinho);
                    }
                }
            }
        }

        return false;

    }

    /**
     * Método para verificar sugestões deo amizade para um usuário.
     * @param nomeUsuario Nome do usuário a ser verificado
     * @return Retorna uma lista de usuários (Vertices) que possuem um grau 2 (amigo do amigo)
     */
    public List<Vertice> sugerirAmizades(String nomeUsuario) {
        Vertice usuario = verificaVertice(nomeUsuario);
        ArrayList<Vertice> sugestoes = new ArrayList<>();

        if (usuario == null) return sugestoes;

        List<Vertice> amigos = listarAmigosDeUmUsuario(usuario);
        for (Vertice amigo : amigos) {
            for (Vertice amigoDoAmigo : listarAmigosDeUmUsuario(amigo)) {
                if (amigoDoAmigo != usuario && !amigos.contains(amigoDoAmigo) && !sugestoes.contains(amigoDoAmigo)) {
                    sugestoes.add(amigoDoAmigo);
                }
            }
        }

        return sugestoes;
    }

    /**
     * Método que printa o Grafo
     */
    public void mostrarGrafo() {
        for (Vertice usuario : vertices) {
            System.out.print(usuario.getValor() + " -> ");
            for (Vertice amigo : listarAmigosDeUmUsuario(usuario)) {
                System.out.print(amigo.getValor() + " ");
            }
            System.out.println();
        }
    }

}
