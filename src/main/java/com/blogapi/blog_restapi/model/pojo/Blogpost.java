package com.blogapi.blog_restapi.model.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blogpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private String publishDate;

    private String imageUrl;

    @ManyToOne
    @JoinTable(name = "blogpost_author",
            joinColumns = @JoinColumn(name = "blogpost_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Author author;

    @ManyToOne
    @JoinTable(name = "blogpost_category",
            joinColumns = @JoinColumn(name = "blogpost_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Category category;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Post_Tag",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags;

    private long viewCount;
    private long likesCount;
    private long dislikesCount;

    @OneToMany(mappedBy = "blogpost")
    private Set<Comment> comments;

}
