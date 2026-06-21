package com.alfred_core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.alfred_core.automation.web.scraping.JinaScraper;
import com.alfred_core.automation.web.scraping.WebPage;

@Service
public class UrlSearchService {

	private final JinaScraper jinaScraper;

	public UrlSearchService(JinaScraper jinaScraper) {
		this.jinaScraper = jinaScraper;
	}

	private static final Pattern URL_PATTERN = Pattern
			.compile("(https?://[\\w\\-\\.]+(:\\d+)?(/[\\w\\-\\.\\?%&=+#]*)*)", 
					Pattern.CASE_INSENSITIVE);

	public static boolean hasUrls(String prompt) {
	    if (prompt == null || prompt.isEmpty()) {
	        return false;
	    }
	    return URL_PATTERN.matcher(prompt).find();
	}
	
	public List<String> extractUrls(String prompt) {
		List<String> urls = new ArrayList<>();
		Matcher matcher = URL_PATTERN.matcher(prompt);
		while (matcher.find()) {
			urls.add(matcher.group());
		}
		return urls;
	}

	public String generatePrompt(List<String> urls) {

		StringBuilder prompt = new StringBuilder();
		for (String url : urls) {
			try {
				WebPage page = jinaScraper.scrape(url);
				prompt.append("SOURCE: ").append(url).append("\n\n");
				prompt.append(page.getContent()).append("\n\n");
			} catch (Exception e) {
				prompt.append("SOURCE: ").append(url).append("\n");

				prompt.append("ERROR: Unable to access URL.").append("\n\n");
			}
		}
		return prompt.toString();
	}

}
