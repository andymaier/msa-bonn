@RestController
class ThisWillActuallyRun {

	@RequestMapping("/hello")
	String home() {
		"Hello World!"
	}
}
