// 리스트 11-3 사전 검색 애플리케이션

Transaction.runVoid(() -> {
    STextField word = new STextField("", 25);
    CellLoop<Boolean> enabled = new CellLoop<>();
    SButton button = new SButton("look up", enabled);
    Stream<String> sWord = button.sClicked.snapshot(word.text);
    IsBusy<String, Optional<String>> ib =
	                     new IsBusy<>(lookup, sWord);
    Stream<String> sDefinition = ib.sOut
	.map(o -> o.isPresent() ? o.get() : "ERROR!");
    Cell<String> definition = sDefinition.hold("");
    Cell<String> output = definition.lift(ib.busy, (def, bsy) ->
        bsy ? "Looking up..." : def);
    enabled.loop(ib.busy.map(b -> !b));
    STextArea outputArea = new STextArea(output, enabled);
    view.add(word, c);
    view.add(button, c);
    view.add(new JScrollPane(outputArea), c);
});
