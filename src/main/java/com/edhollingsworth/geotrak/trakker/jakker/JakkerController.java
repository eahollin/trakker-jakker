package com.edhollingsworth.geotrak.trakker.jakker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller allows monitoring and initiation of TrakkerJakker utility.
 *
 * @author Ed Hollingsworth
 */
@RestController
public class JakkerController {
	private final Log log = LogFactory.getLog(JakkerController.class.getName());

	@Autowired
	private TrakkerJakker tj;

	/**
	 * Run the process.
	 */
	@CrossOrigin(origins = "*")
    @GetMapping(path = "/jakk")
    public void jakkIt(@RequestParam(required=false) String geoId, @RequestParam(required=false) Integer iterations) {
		log.debug("Received geoId="+geoId+", iterations="+iterations);
		
		log.info("Jakking things up...");
		tj.process(geoId, iterations);
    }
	
	/**
	 * Output the status.
	 */
	@CrossOrigin(origins = "*")
    @GetMapping(path = "/status")
    public String getStatus() {
		log.info("Attempting to get status...");
		
		return tj.getStatus();
    }
}
