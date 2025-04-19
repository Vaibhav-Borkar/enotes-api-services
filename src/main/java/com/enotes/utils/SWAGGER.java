/**
 * @author Vaibhav Borkar
 * @explanation In this file we declare the swagger description in a constant form so it can improove the readability.
 */
package com.enotes.utils;

public class SWAGGER {

	public static final String VERIFY_ACCOUNT = "This API is used to verify a user's email address during the initial registration process. "
			+ "When a user registers for the first time, a verification email containing a unique link is sent to their registered email address. "
			+ "The user must click on that link to activate their account. "
			+ "This endpoint handles the verification logic and confirms that the user's email address is valid and that the account can now be considered verified and active.";

	public static final String SEND_EMAIL_RESET = "This API is responsible for sending a password reset email to users who have forgotten their password or wish to change it. "
			+ "When a password reset is initiated, a unique verification link is sent to the user's registered email address. "
			+ "This step ensures that the password reset request is legitimate and that the email address belongs to the user attempting to reset the password.";

	public static final String VERIFY_PASSWORD_LINK = "This API handles the verification of the password reset link that was sent to the user's email. "
			+ "When a user clicks on the link in the email, this endpoint checks whether the link is valid, not expired, and associated with a legitimate reset request. "
			+ "Only after this verification can the user proceed to reset their password.";

	public static final String RESET_PASSWORD = "This API allows a user to set a new password after successfully verifying their password reset request. "
			+ "Once the password reset link has been validated through the verification endpoint, the user can use this API to enter and confirm a new password. "
			+ "This process helps users recover access to their account in a secure manner.";

	public static final String REGISTER_USER = "This API is used to register a new user into the system. "
			+ "It collects necessary user information such as name, email, and password, and creates a new account. "
			+ "After registration, a verification email may be sent to confirm the user's email address before activating the account.";

	public static final String LOGIN_USER = "This API is used for authenticating an existing user. "
			+ "It verifies the provided credentials, such as email and password, and if valid, allows the user to access protected resources. "
			+ "Upon successful login, a token or session is usually generated for further authentication.";

	public static final String SAVE_CATEGORY = "This API is used to create and save a new category in the system. "
			+ "It accepts category details such as name and description, and stores them in the database.";

	public static final String GET_ALL_CATEGORY = "This API retrieves a list of all categories available in the system. "
			+ "It is useful for displaying category options to users or for administrative purposes.";

	public static final String ACTIVE_CATEGORY = "This API is used to activate or enable a specific category. "
			+ "Activating a category makes it available for use or selection within the application.";

	public static final String GET_CATEGORY_BY_ID = "This API retrieves the details of a specific category using its unique ID. "
			+ "It is typically used when viewing or editing category information.";

	public static final String DELETE_CATEGORY = "This API is used to delete a category from the system using its ID. "
			+ "Once deleted, the category will no longer be available or visible in the application.";

	public static final String UPDATE_CATEGORY = "This API updates the details of an existing category. "
			+ "It allows modifying information such as the category's name, description, or status.";

	public static final String ADD_FAVOURITE_NOTE = "This API allows a user to mark a specific note as a favourite. "
			+ "It is used to easily highlight or save important notes for quick access later.";

	public static final String UN_FAVOURITE_NOTE = "This API allows a user to remove a note from their list of favourites. "
			+ "It is used when a note is no longer considered important or needs to be unmarked.";

	public static final String GET_USER_FAVOURITE_NOTE = "This API retrieves all notes that a specific user has marked as favourite. "
			+ "It helps users quickly access and manage their most important or preferred notes.";

	public static final String SAVE_NOTE = "This API is used to create and save a new note. "
			+ "It accepts note details such as title, content, and any attached files, and stores them securely in the database.";

	public static final String GET_ALL_NOTES = "This API retrieves a list of all notes available in the system. "
			+ "It is typically used for displaying all notes, especially in admin views or dashboards.";

	public static final String DOWNLOAD_FILE = "This API allows users to download files that are attached to a specific note. "
			+ "It provides access to previously uploaded files for offline viewing or editing.";

	public static final String GET_USER_NOTE = "This API retrieves all notes created by a specific user. "
			+ "It filters notes based on the user's identity to ensure users can only access their own content.";

	public static final String UPDATE_NOTE = "This API is used to update the content or details of an existing note. "
			+ "Users can modify the title, content, or attached files of the note.";

	public static final String SOFT_DELETE_NOTE = "This API performs a soft delete on a note, meaning the note is not permanently removed but moved to the recycle bin. "
			+ "This allows users to recover the note later if needed.";

	public static final String RESTORE_NOTE = "This API restores a previously soft-deleted note from the recycle bin. "
			+ "It makes the note available again in the user's active notes list.";

	public static final String NOTE_RECYCLE_BIN = "This API retrieves all notes that have been soft deleted and are currently stored in the recycle bin. "
			+ "It helps users view and manage deleted notes before they are permanently removed.";

	public static final String FILE_UPLOAD = "This API allows users to upload files that can be attached to a note. "
			+ "It handles the storage and management of file data alongside notes.";

	public static final String HARD_DELETE_NOTE = "This API permanently deletes a note from the system. "
			+ "Once a note is hard deleted, it cannot be recovered.";

	public static final String EMPTY_RECYCLE_BIN = "This API permanently deletes all notes from the recycle bin. "
			+ "It is used to free up storage and ensure that no soft-deleted notes remain in the system.";

	public static final String COPY_NOTE = "This API creates a duplicate of an existing note. "
			+ "It is useful when users want to reuse a note's content without modifying the original.";

	public static final String SEARCH_NOTE = "This API allows users to search for notes based on keywords or filters such as title, content, or tags. "
			+ "It helps users quickly find specific notes from their collection.";
	public static final String SAVE_TODO = "This API is used to create and save a new todo task. "
			+ "It accepts details such as the task name, description, due date, and stores the task in the system for the user.";

	public static final String GET_TODO_BY_ID = "This API retrieves the details of a specific todo task using its unique ID. "
			+ "It is useful for viewing or editing a particular task.";

	public static final String GET_TODO_BY_USER = "This API fetches all todo tasks associated with a specific user. "
			+ "It helps users view their personal list of pending, completed, or upcoming tasks.";

	public static final String UPDATE_TODO = "This API is used to update an existing todo task. "
			+ "It allows the user to change task details like the title, description, due date, or completion status.";

	public static final String GET_PROFILE = "This API retrieves the profile details of the currently logged-in user. "
			+ "It provides information such as name, email, and other personal or account-related data.";

	public static final String CHANGE_PASSWORD = "This API allows a user to securely change their current password. "
			+ "The user must provide their existing password for verification, along with the new password they wish to set.";

}
