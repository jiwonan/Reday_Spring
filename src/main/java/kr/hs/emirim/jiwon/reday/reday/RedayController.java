package kr.hs.emirim.jiwon.reday.reday;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RedayController {
	
	@Autowired private ArticleRepository articleRepository;  
	@Autowired private UserRepository userRepository;
	@Autowired private CountriesRepository countriesRepository;
	
	@GetMapping(value="/test", produces=MediaType.APPLICATION_JSON_VALUE)
	public String testAd() {
		return "{\"connect: \" success}";
	}
	
	@GetMapping(value="/users/{email}", produces=MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable String email) {
		return userRepository.findByEmail(email);
	}
	
	// 한 명의 유저가 쓴 게시글을 모두 가져옴.
	@GetMapping(value="{username}/articles", produces=MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Article> readArticlesData(@PathVariable String username) {
		User user = userRepository.findByUserName(username);
		
    	return user.getArticles();
    }
	
	// 모든 게시글을 가져옴.
	@GetMapping(value="/articles", produces=MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Article> readArticlesDataAll() {
		return articleRepository.findAll();
	}
		
	// 모든 나라 불러옴.
	@GetMapping(value="/all_countries", produces=MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Countries> readCountriesDataAll() {
		return countriesRepository.findAll();
	}
	
	//한 나라의 게시글을 모두 가져옴
	@GetMapping(value="{country}/articles", produces=MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Article> readCountryArticlesDataAll(@PathVariable String country) {
		Countries countries = countriesRepository.findByCountry(country);
		
		return countries.getArticles();
	}
		
	/*
	// get user_name
	@GetMapping(value="{email}/getusername", produces=MediaType.TEXT_PLAIN_VALUE)
	public String getUsername(@PathVariable String email) {
		User user = userRepository.findByEmail(email);
		
		return user.getUserName();
	}
	*/
		
	// 글쓰기.
	@PostMapping(value="/{username}/articles/{country}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String createArticle(@PathVariable String username,
			@RequestParam("title") String title,
			@RequestParam("contents") String contents,
			@RequestParam("file") MultipartFile file,
			@PathVariable String country) throws IOException {
		User user = userRepository.findByUserName(username);
		Countries countries = countriesRepository.findByCountry(country);
		
		String fileName = (new Date()).getTime() + "_" + username + "_" +file.getOriginalFilename();
		
		File file_f = new File("uploads/"+username);
		if(!file_f.exists()) file_f.mkdir();
		
		System.out.println(file.getOriginalFilename());
		BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("uploads/"+ username + "/" + fileName));
		
		while(bis.available()>0) {
			bos.write(bis.read());
		}
		
		bos.close();
		bis.close();
		
		// DB에 저장.
		Article article = new Article(title, contents, 0);
		article.setFileLocation("uploads/"+ username + "/" + fileName);
		article.setUser(user);
		article.setCountries(countries);
		articleRepository.save(article);
		
		return "true";
	}
	
	// JoinIn
	@PostMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestParam(value="username") String username,
    		@RequestParam(value = "usernickname") String usernickname,
    		@RequestParam(value="password") String password, @RequestParam(value="email") String email) {
		System.out.println("create user");
    	User user = new User(username, usernickname, password, email);
    	userRepository.save(user);
    	
        return user;
    }
	
	// login
	@PostMapping(value="/users/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public String loginUser(@RequestParam(value="email") String email, @RequestParam(value="password") String password) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			return "{\"success\": false, \"reason\": \"user not found.\"}";
		}
		else {
			if(!(user.getPassword().equals(password))) {
				return "{\"success\": false, \"reason\": \"password is wrong.\"}";
			}
			else return "{\"success\": true}";
		}
	}
	
	/*
	@GetMapping("/imgupload")
	public String uploadImg(@RequestParam Map<String, MultipartFile> fileMap) {
		
		
		return "OK";
	}
	*/
	
}
