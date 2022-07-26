package br.dev.pedrolamarao.accounting.service;

import java.net.URI;

public record Stored <T> (URI uri, T value) { }
