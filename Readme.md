# Teste de Integração

O controller é anotado com `@GetMapping, @PostMapping, @PatchMapping, @PutMapping, @DeleteMapping, 
@RequestMapping`.

Ele recebe entrada por meio de parâmetros anotados com `@PathVariable, @RequestBody e @RequestParam` que são preenchidos automaticamente a partir da solicitação HTTP recebida.

O controller então trabalha com esses parâmetros, chamando a lógica de negócios antes de retornar um objeto Java simples, 
que é automaticamente mapeado em JSON e gravado no corpo da resposta HTTP por padrão.

## Responsabilidades
- Ouvir as requisições - `obs`
- Desserializar entrada - `obs`
- Validar entrada - `obs`
- Chame a lógica de negócios
- Serialize a saída - `obs`
- Traduzir exceções - `obs`

Um simples teste de unidade não cobrirá a camada HTTP. 

Precisamos um teste de integração que testa a integração entre o código do nosso controlador e os componentes que o Spring fornece para suporte HTTP.


Um teste de integração com o Spring aciona um contexto de aplicativo Spring que contém todos os beans de que precisamos.

Isso inclui beans de estrutura que são responsáveis por ouvir determinadas URLs, 
serializar e desserializar para JSON e traduzir exceções para HTTP.
Esses beans avaliarão as anotações que seriam ignoradas por um simples teste de unidade.


O Spring Boot fornece a anotação @WebMvcTest para iniciar um contexto de aplicativo que contém apenas os beans necessários para testar um controlador da web.

Todo aplicativo Spring requer um Contexto, um local onde todos os componentes são registrados.
Quando queremos usar algum Componente desses que estão no tal Contexto usamos a Annotation @Autowired e com ela dizemos para o Spring que ele deve procurar em seu Contexto algum Componente equivalente ao que precisamos e Injetar na variável onde estamos usando essa Annotation
