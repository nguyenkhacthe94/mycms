package com.example.cms.config;

import com.example.cms.model.Post;
import com.example.cms.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {

    private final PostRepository postRepository;

    @Autowired
    public DatabaseSeeder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void seedDatabase() {
        if (postRepository.count() == 0) {
            List<Post> initialPosts = new ArrayList<>();
            initialPosts.add(createPost("First Post", "A short intro",
                    "<h1>Welcome to the first Post</h1>\n" +
                            "<p>Here is some content for our very first post. This is sample rich text</p>",
                    "https://via.placeholder.com/150", LocalDateTime.now().minusDays(5)));
            initialPosts.add(createPost("Second Post", "A detailed guide",
                    "<h1>Diving Deeper into the Second Post</h1>\n" +
                            "<ul>\n" +
                            "<li>Point 1 - An interesting point</li>\n" +
                            "<li>Point 2 - A crucial aspect</li>\n" +
                            "<li>Point 3 - More information</li>\n" +
                            "</ul>",
                    "https://via.placeholder.com/150", LocalDateTime.now().minusDays(2)));

            initialPosts.add(createPost("Third Post", "Final thought",
                    "<p><em>This is the third post, summarizing what's covered earlier.</em></p>\n" +
                            "<p>This is some emphasis text and a link <a href='https://www.example.com'>here</a></p>",
                    "https://via.placeholder.com/150", LocalDateTime.now()));

            postRepository.saveAll(initialPosts);
            System.out.println("Database seeded with initial posts.");
        } else {
            System.out.println("Database already contains data. Seeding skipped.");
        }
    }

    private Post createPost(String title, String subtitle, String content, String imageUrl, LocalDateTime createdDate) {
        Post post = new Post();
        post.setTitle(title);
        post.setSubtitle(subtitle);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setCreatedDate(createdDate);
        return post;
    }
}