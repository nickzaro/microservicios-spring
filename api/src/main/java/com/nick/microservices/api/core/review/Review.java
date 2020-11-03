package com.nick.microservices.api.core.review;

public class Review {

    private final int productoId;
    private final int reviewId;
    private final String author;
    private final String subject;
    private final  String content;
    private  final String serviceAddress;

    public Review() {
        this.productoId = 0;
        this.reviewId=0;
        this.author = null;
        this.subject = null;
        this.content = null;
        this.serviceAddress = null;
    }

    public Review(int productoId, int reviewId, String author, String subject, String content, String serviceAddress) {
        this.productoId = productoId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public int getProductoId() {
        return productoId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }
}
