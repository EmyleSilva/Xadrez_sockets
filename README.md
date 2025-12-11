# Xadrez Educativo 

O Xadrez Educativo, desenvolvido pelos alunos ***Arthur Moreira***, ***Breno Arouca*** e ***Emyle Silva***, tem como objetivo aplicar os conceitos de sockets para comunicação em rede em aplicações distribuídas. Este projeto foi realizado durante a disciplina ***DEC000098 - Redes de Computadores I da Universidade Estadual de Santa Cruz - UESC***.

## 1. Visão Geral do Software

### 1.1 Propósito
Este software implementa uma aplicação educacional de xadrez baseada em rede, focada no ensino das regras e movimentações das peças. Diferente de um jogo tradicional, o sistema oferece dois modos principais: educativo (para aprendizado) e simulação (para prática livre), permitindo que usuários explorem o xadrez em sessões individuais sem competição direta.

### 1.2 Motivação da Arquitetura
A escolha da arquitetura baseada em sockets foi motivada por:
- **Sessões isoladas:** Cada cliente mantém seu próprio estado indepedente.
- **Processamento centralizado:** Validação de movimentos e regras no servidor.
- **Escalabilidade:** Suporte a múltiplos usuários simultâneos sem interação entre eles.
- **Controle Educacional:** Garantia que as explicações e validações sejam consistentes.

### 1.3 Funcionamento do Software

A aplicação é dividida em dois componentes principais que se comunicam via sockets TCP: Servidor e Cliente. 

#### **Servidor (Server.java)**
---

O servidor é o componente central, que processa todas as requisições de validação e fornece informações educativas sobre as peças de xadrez.

**Arquitetura de Conexão**

- **Modelo Cliente-Servidor Síncrono:** O servidor opera em loop infinito que aceita uma conexão por vez (server.accept()). Cada requisição é processada imediatamente após a conexão e é fechada após a resposta, tornando-o ***stateless***.
- **Porta Fixa:** O servidor escuta na porta 8089 por padrão.     
- **Protocolo de Comunicação:** Todas as mensagens são trocadas no formato JSON serializado utilizando a biblioteca GSON.

**Processamento de Requisições**

O servidor implementa uma máquina de estados simples baseada no tipo de requisião (Tipo):

1. **GET_EXP** - Modo Explicativo:
    
    - Recebe o nome de uma peça (BISPO, TORRE ou CAVALO)
    - Retorna uma explicação textual da peça e uma sequência pré-definida de movimentos para simulação
    - As explicações são geradas pelos métodos explicacao() das classes Bispo, Cavalo e Torre
    - Os destinos de simulação são arrays fixos contendo coordenadas X,Y
2. **GET_PECA** - Adição de Peças (Modo Simulação):
    
    - Recebe: tipo da peça, cor, posição de destino e estado atual do tabuleiro
    - Valida: Cria uma nova instância da peça solicitada
    - Posiciona: Configura a peça na posição especificada
    - Retorna: O tabuleiro atualizado com a nova peça adicionada 

3. **RMOV_PECA** - Movimentação de Peças (Modo Simulação):
    - Recebe: coordenadas de origem e destino, e o tabuleiro atual
    - Valida: Verifica se há uma peça na posição de origem
    - Executa: Chama tabuleiro.movimentarPeca() para validar e executar o movimento
    - Retorna: O tabuleiro atualizado com o movimento aplicado ou status de erro

**Gerenciamento de Estados**

- O servidor é ***stateless***: não mantém estado entre requisições
- Todo o estado do tabuleiro é enviado pelo cliente em cada requisição e retornado atualizado
- Isso permite múltiplos clientes independentes sem necessidade de sincronização
- O modelo é ideal para sessões educacionais individuais

#### **Cliente (Interface Gráfica)**
---
O cliente é a interface visual com a qual o usuário interage. Existem duas interfaces principais:

1. **ModoExplicavel.java** 
    
    - ***Propósito:*** Ensinar as regras de movimentação das peças
    - ***Funcionamento:***
        
        - O usuário seleciona uma peça disponível para aprender 
        - O cliente envia requisição GET_EXP ao servidor
        - Exibe a explicação retornada em uma caixa de diálogo com rolagem
        - Oferece opção de simulação dos movimentos
        - Executa uma animação passo-a-passo usando Timer para demonstrar os movimentos válidos

2. **ModoSimulacao.java**
    
    -  ***Propósito:*** Permitir experimentação livre com as peças 
    - ***Funcionamento:***
        
        - *Adição de Peças:* O usuário seleciona uma peça e cor, depois clica no tabuleiro para posicioná-la
        - *Movimentação:* Clique-duplo: primeiro na peça, depois no destino
        - *Validação:* Todas as ações são validadas pelo servidor
        - *Atualização Visual:* O tabuleiro é redesenhado após cada operação válida 

    - **Máquinas de Estado do Cliente**
    ```markdown
        Estado Inicial → Seleção de Peça (controller=false) → Posicionamento
        
        ou

        Seleção para Movimento (canMakeMovement=false) → Destino (canMakeMovement=true)
    ```
## 2. Protocolo da Camada de Aplicação

Define como o cliente e o servidor se comunicam na aplicação.

### 2.1 Formato e Transporte
---

As mensagens são trocadas por um socket TCP.

- **Formato:** As mensagens da aplicação são objetos JSON serializados para string usando a biblioteca GSON.
- **Delimitador:** Cada mensagem é terminada por um caractere de quebra de linha ('\n'), permitindo que o receptor separe as mensagens usando readLine().
- **Serialização Personalizada:** Para suportar polimorfismo das peças de xadrez, utiliza-se um PecaAdapter customizado no Gson.

**Estrutura Padrão das Mensagens**

- **Requisição (Cliente - Servidor):

```json
{
  "tipo": "GET_EXP|GET_PECA|RMOV_PECA",
  "peca": "BISPO|TORRE|CAVALO",
  "cor": 0|1,
  "posX": 0-7,
  "posY": 0-7,
  "destinoX": 0-7,
  "destinoY": 0-7,
  "t": { ... }  // Objeto Tabuleiro
}
```

- **Resposta (Servidor - Cliente)**

```json
{
  "tipo": "POST_EXP|POST_PECA|MOV_PECA",
  "status": "OK|PECA_NULL|INVALIDO",
  "mensagem": "Texto explicativo...",
  "destinosSimulacao": [x1,y1,x2,y2,...],
  "tabuleiro": { ... }  // Objeto Tabuleiro atualizado
}
```
### 2.2 Fluxo de Estados e Mensagens 
---

### 2.2.1 Modo Educativo (Explicavel)

**Consulta de Explicação:**

```json
Cliente -> GET_EXP {"peca": "BISPO"}
Servidor -> POST_EXP {
  "mensagem": "O bispo se move apenas nas diagonais...",
  "destinosSimulacao": [7,7,5,5,7,3],
  "status": "OK"
```

**Simulação dos Movimentos:**

```json
Cliente exibe explicação e pergunta se quer simular
Se usuário confirmar:
  Cliente executa animação local com destinos [7,7,5,5,7,3]
```

### 2.2.2 Modo Simulação

**Adição de Peça no Tabuleiro:**

```json
Cliente -> GET_PECA {
  "peca": "TORRE",
  "cor": 0,
  "destinoX": 3,
  "destinoY": 3,
  "t": { ...estado atual do tabuleiro... }
}
Servidor -> POST_PECA {
  "tabuleiro": { ...tabuleiro com torre adicionada... },
  "status": "OK"
}
```

**Movimentação de Peça**

```json
Cliente (primeiro clique) -> Seleciona peça localmente
Cliente (segundo clique) -> RMOV_PECA {
  "posX": 3,
  "posY": 3,
  "destinoX": 5,
  "destinoY": 5,
  "t": { ...estado atual... }
}
Servidor (valida movimento) -> MOV_PECA {
  "tabuleiro": { ...tabuleiro com movimento aplicado... },
  "status": "OK|INVALIDO"
}
```

**Movimento Inválido**

```json
Cliente -> RMOV_PECA { ...movimento ilegal... }
Servidor -> MOV_PECA {
  "tabuleiro": { ...tabuleiro inalterado... },
  "status": "INVALIDO"
}
```

### 2.2.3 Finalização de Sessão

**Retorno ao Menu Principal:**

```json
Cliente fecha janela ModoSimulacao/ModoExplicavel
Cliente limpa tabuleiro local: t.limparTabuleiro()
Cliente abre MenuPrincipal
```

### 2.3 Dicionário de Mensagens 
---

### 2.3.1 Mensagens Clientes -> Servidor 

***GET_EXP***

- **Quando:** Enviada pelo cliente quando o usuário clica em "EXPLICAR [Peça]" no Modo Educativo.
- **Propósito:** Solicitar explicação textual e destinos de simulação para uma peça específica.
- **Payload:** 
```json
{"peca": "BISPO|TORRE|CAVALO"}
```

***GET_PECA***
- **Quado:** Enviada pelo cliente após o usuário selecionar uma peça, cor e clicar no tabuleiro no Modo Simulação.
- **Propósito:** Solicitar ao servidor que adicione uma nova peça ao tabuleiro
- **Payload:**
```json
{
  "peca": "BISPO|TORRE|CAVALO",
  "cor": 0,           // 0=Branco, 1=Preto
  "destinoX": 3,      // Posição X (0-7)
  "destinoY": 3,      // Posição Y (0-7)
  "t": { ... }        // Estado atual do tabuleiro
}
```

***RMOV_PECA***
- **Quado:** Enviada pelo cliente quando o usuário seleciona uma peça e clica em uma posição de destino no Modo Simulação.
- **Propósito:** Solicitar ao servidor que valide e execute o movimento de uma peça.
- **Payload:**
```json
{
  "posX": 3,          // Posição X da peça a mover
  "posY": 3,          // Posição Y da peça a mover
  "destinoX": 5,      // Destino X
  "destinoY": 5,      // Destino Y
  "t": { ... }        // Estado atual do tabuleiro
}
```

### 2.3.2 Mensagens Servidor -> Cliente

***POST_EXP***
- **Quado:** Enviada pelo servidor em resposta a uma requisição GET_EXP.
- **Propósito:** Fornecer explicação educativa e destinos para simulação de movimento.
- **Payload:**
```json
{
  "mensagem": "O cavalo se move em formato de 'L'...",
  "destinosSimulacao": [1,2,3,1,5,2],  // Array de coordenadas [x1,y1,x2,y2,...]
  "status": "OK"
}
```

***POST_PECA***
- **Quado:** Enviada pelo servidor em resposta a uma requisição GET_PECA.
- **Propósito:** Confirmar que a peça foi adicionada ao tabuleiro ou indicar erro.
- **Payload:**
```json
{
  "tabuleiro": { ... },  // Tabuleiro com a nova peça adicionada
  "status": "OK|PECA_NULL|INVALIDO"
}
```

***MOV_PECA***
- **Quado:** Enviada pelo servidor em resposta a uma requisição RMOV_PECA.
- **Propósito:** Confirmar que o movimento foi executado ou indicar que é inválido.
- **Payload:**
```json
{
  "tabuleiro": { ... },  // Tabuleiro após movimento (ou inalterado se inválido)
  "status": "OK|INVALIDO|PECA_NULL"
}
```

### 2.3.3 Status de Resposta

***OK***
- **Significado:** Operação realizada com sucesso.
- **Quando Usado:** 
    - Em POST_EXP quando a peça existe
    - Em POST_PECA quando a peça foi adicionada
    - Em MOV_PECA quando o movimento é válido

***PECA_NULL***
- **Significado:** Peça não encontrada ou tipo de peça inválido.
- **Quando Usado:** 
    - Em POST_EXP quando o nome da peça não é reconhecido
    - Em POST_PECA quando não há peça na posição de origem
    - Em MOV_PECA quando não há peça na posição de origem

***INVALIDO***
- **Significado:** Movimento inválido de acordo com as regras do xadrez.
- **Quando Usado:** 
    - Em MOV_PECA quando o movimento não segue as regras da peça ou quando o caminho está bloqueado.

### 2.3.4 Estrutura do Objeto no Tabuleiro
**Serializado via Gson com PecaAdapter:**
```json
{
  "matriz": [
    [null, peca, null, ...],  // 8x8
    [...],
    ...
  ],
  "capturadasBrancas": [peca1, peca2, ...],
  "capturadasPretas": [peca3, peca4, ...]
}
```

### 2.3.5 Estrutura do Objeto Peca
**Serializado via PecaAdapter:**
```json
{
  "tipo": "BISPO|TORRE|CAVALO",
  "id": 123,
  "cor": 0|1,
  "posicaoAtualX": 3,
  "posicaoAtualY": 3,
  "posicaoOrigemX": 0,
  "posicaoOrigemY": 0
}
```

### 2.4 Motivação da Escolha de TCP

**Confiabilidade:**

O TCP garante a entrega ordenada e sem perdas, o que é essencial para a aplicação pois as explicações devem chegar completas e as respostas de movimentos não podem ser perdidas.  

**Simplicidade do Modelo Requisição-Resposta:**

Cada operação segue o padrão: Conexão -> Requisição -> Resposta -> Desconexão, mantendo o servidor stateless e permitindo a conexão de vários clientes simultâneamente em sessões individuais.
O TCP natural suporta esse fluxo sem overhead desnecessário.

**Ordenação para Simulações:**

É importante que os destinos de simulação sejam processados em ordem, além disso, a animação passo-a-passo depende da sequência correta. 

**Controle Nativo de Fluxo:**

- **Java Sockets:** Abstração simples sobre TCP
- **Socket, ServerSocket:** API direta e eficiente
- **BufferedReader.readLine():** Funciona perfeitamente com delimitador \n

**Por que não UDP neste caso?**
- **Conteúdo crítico:** Explicações educativas não podem ser perdidas
- **Validações importantes:** Usuário precisa de confirmação confiável
- **Ordem crucial:** Simulações dependem de sequência
- **Overhead aceitável:** Conexões por operação são esporádicas
- **Implementação simples:** TCP requer menos código de confiabilidade

Este protocolo sobre TCP provou ser ideal para o Xadrez Educacional, equilibrando confiabilidade necessária para o contexto educativo com simplicidade de implementação.

### 2.5 Diagrama de Sequência

Abaixo está o diagrama de sequência consolidado do sistema (Cliente ↔ Servidor), cobrindo os fluxos de Explicação, Adição e Movimentação de peças:

![Diagrama de Sequência](diagramaSequencia.png)

## 3 Requisitos Mínimos de Funcionamento

Para garantir a execução correta do software (Cliente e Servidor), o ambiente deve atender aos seguintes requisitos:

### 3.1 Software
* **Sistema Operacional:** Windows 10/11, Linux (qualquer distribuição com suporte a Java) ou macOS.
* **Java:** JDK 17 ou superior instalado e configurado no PATH do sistema.
* **Gerenciador de Dependências:** Maven (caso vá compilar o projeto a partir do código fonte).

### 3.2 Hardware
* **Processador:** Intel Core i3 / AMD Ryzen 3 ou superior (dual-core).
* **Memória RAM:** Mínimo de 2GB (recomendado 4GB para execução fluida da JVM).
* **Armazenamento:** 50 MB de espaço livre em disco.
* **Rede:** Adaptador de rede (Wi-Fi ou Ethernet) funcional.

### 3.3 Configuração de Rede
* **Porta:** A porta **8089** (TCP) deve estar liberada no Firewall da máquina onde o Servidor será executado.
* **Conectividade:** As máquinas (Cliente e Servidor) devem estar conectadas à mesma rede local (LAN) ou acessíveis via VPN/IP Público.
## 4. Autores

Este projeto foi desenvolvido pelos seguintes alunos:

* **Arthur Moreira**
* **Breno Arouca**
* **Emyle Silva**