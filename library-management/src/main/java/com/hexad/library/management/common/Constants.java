package com.hexad.library.management.common;

public interface Constants {

	String NOT_ALLOWED_TO_BARROW = "NOT_ALLOWED_TO_BARROW";
	String OUT_OF_STOCK = "OUT_OF_STOCK";

	interface Swagger {
		String PROJECT_NAME = "library-management";
		String PROJECT_DESCRIPTION = "Service to Library";
		String TEAM_NAME = "library management";

		interface Tags {

			interface BookController {
				String NAME = "Book";
				String DESCRIPTION = "Book Controller";
			}
		}
	}

}
