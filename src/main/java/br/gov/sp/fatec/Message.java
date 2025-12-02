package br.gov.sp.fatec;

public record Message(
        String uniqueId,
        String content,
        long timestamp // epoch milliseconds
) {
}
