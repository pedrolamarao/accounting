package br.dev.pedrolamarao.accounting.service;

import java.net.URI;
import java.util.List;

public record Paged <T> (URI current, URI next, URI previous, List<T> values) { }
