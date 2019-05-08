package hello.controller;

import hello.entity.Blog;
import hello.entity.BlogListResult;
import hello.entity.BlogResult;
import hello.entity.Result;
import hello.entity.User;
import hello.service.AuthService;
import hello.service.BlogService;
import hello.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class BlogController {
    private final AuthService authService;
    private final BlogService blogService;

    @Inject
    public BlogController(AuthService authService, BlogService blogService) {
        this.authService = authService;
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public BlogListResult getBlogs(@RequestParam("page") Integer page, @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page < 0) {
            page = 1;
        }

        return blogService.getBlogs(page, 10, userId);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult getBlog(@PathVariable("blogId") int blogId) {
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogResult newBlog(@RequestBody Map<String, String> param) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.insertBlog(fromParam(param, user)))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult updateBlog(@PathVariable("blogId") int blogId, @RequestBody Map<String, String> param) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.updateBlog(blogId, fromParam(param, user)))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public Result deleteBlog(@PathVariable("blogId") int blogId) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.deleteBlog(blogId, user))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    private Blog fromParam(Map<String, String> params, User user) {
        Blog blog = new Blog();
        String title = params.get("title");
        String content = params.get("content");
        String description = params.get("description");

        AssertUtils.assertTrue(StringUtils.isNotBlank(title) && title.length() < 100, "title is invalid!");
        AssertUtils.assertTrue(StringUtils.isNotBlank(content) && content.length() < 10000, "content is invalid");

        if (StringUtils.isBlank(description)) {
            description = content.substring(0, Math.min(content.length(), 10)) + "...";
        }

        blog.setTitle(title);
        blog.setContent(content);
        blog.setDescription(description);
        blog.setUser(user);
        return blog;
    }
}
