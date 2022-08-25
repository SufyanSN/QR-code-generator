package com.demo.qr.demo;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.json.JSONException;
import org.thymeleaf.util.StringUtils;


@RestController
public class QRCodeController {
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
	private final int WIDTH = 300;
	private final int HEIGHT = 300;
	private final String QR_TEXT = "Spring Boot REST API to generate QR Code - Websparrow.org";



	@GetMapping("/greeting-from-dhruv")
	public ModelAndView greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		ModelAndView mav = new ModelAndView("developer");
		Developer dev = new Developer();
		mav.addObject("developer", dev);
		return mav;
	}

	/*
	@PostMapping("/genrateQRCode")
	public ModelAndView QRCode() {
		ModelAndView mav = new ModelAndView("greeting");
		return mav;
	}
	 */

	@PostMapping("/generateQRCode")
	public ResponseEntity<byte[]> generateQRCode(@ModelAttribute Developer developer) throws Exception{
			if(developer.isWifi()){
				return  ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(QRCodeGenerator.getQRCodeImage(generateJsonWithWifi( developer),WIDTH,HEIGHT));
			}
			else {
				return  ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(QRCodeGenerator.getQRCodeImage(generateJson( developer),WIDTH,HEIGHT));
		}
	}

	String generateJson(Developer developer) throws JSONException{
		JSONObject jsonObjstr = new JSONObject();

		jsonObjstr.put("FirstName", StringUtils.trim(developer.getFirstName()));
		jsonObjstr.put("LastName", StringUtils.trim(developer.getLastName()));
		jsonObjstr.put("Email", StringUtils.trim(developer.getEmail()));

		return jsonObjstr.toString();
	}
	String generateJsonWithWifi(Developer developer) throws JSONException{
		JSONObject jsonObjectStr = new JSONObject();


		jsonObjectStr.put("FirstName",StringUtils.trim(developer.getFirstName()));
		jsonObjectStr.put("LastName",StringUtils.trim(developer.getLastName()));
		jsonObjectStr.put("Email",StringUtils.trim(developer.getEmail()));
		jsonObjectStr.put("WifiSsId",StringUtils.trim(developer.getWifissid()));
		jsonObjectStr.put("WifiPassword",StringUtils.trim(developer.getWifipassword()));
		jsonObjectStr.put("SecurityType",StringUtils.trim("WPA"));

		return jsonObjectStr.toString();

	}
}
