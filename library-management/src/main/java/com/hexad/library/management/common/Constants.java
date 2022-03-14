package com.hexad.library.management.common;

public interface Constants {

	interface Swagger {
		String PROJECT_NAME = "library-management";
		String PROJECT_DESCRIPTION = "Service to Library";
		String TEAM_NAME = "library management";

		interface Tags {

			interface BookController {
		        String NAME = "Book";
		        String DESCRIPTION = "Book Controller";
		      }

		      interface BookStorageController {
		        String NAME = "Book Storage";
		        String DESCRIPTION = "Library Management";
		      }
		}
	}

	interface Summary {
		String APP_NAME = "appName";
		String APP_VERSION = "appVersion";
		String APP_PROFILE = "activeProfile";
		String APP_PROCESS_ID = "processID";
	}

	interface Paths {
		String BASE_PATH = "/api/v1/";

		interface ApiPaths {
			String ORDER = "order";
			String CREATE_ORDER = "create";
		}
	}

	String NOT_ALLOWED_TO_BARROW = "NOT_ALLOWED_TO_BARROW";
	String OUT_OF_STOCK = "OUT_OF_STOCK";
}
