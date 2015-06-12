package teammates.test.cases.ui.pagedata;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.datatransfer.StudentProfileAttributes;
import teammates.common.util.Const;
import teammates.ui.controller.StudentProfilePageData;
import teammates.ui.template.StudentProfileEditBox;
import teammates.ui.template.StudentProfileUploadPhotoModal;

public class StudentProfilePageDataTest {
    
    private StudentProfileAttributes spa;
    private AccountAttributes acct;
    private String editPicture;
    private String pictureUrl;

    @Test
    public void testAll() {
        StudentProfilePageData sppd = initializeFirstData();
        testProfileEditBox(sppd.getProfileEditBox());
        testUploadPhotoModal(sppd.getUploadPhotoModal());
        
        sppd = initializeSecondData();
        testProfileEditBox(sppd.getProfileEditBox());
        testUploadPhotoModal(sppd.getUploadPhotoModal());
    }

    // first data has pictureKey and no null fields
    private StudentProfilePageData initializeFirstData() {
        spa = new StudentProfileAttributes("valid.id.2", "short name", "e@mail2.com", "inst", "nationality",
                                           "male", "more info", "pictureKey");
        acct = new AccountAttributes("valid.id", "full name", false, "e@mail1.com", "inst", spa);
        editPicture = "false";
        pictureUrl = Const.ActionURIs.STUDENT_PROFILE_PICTURE
                   + "?" + Const.ParamsNames.BLOB_KEY + "=" + spa.pictureKey
                   + "&" + Const.ParamsNames.USER_ID + "=" + acct.googleId;
        return new StudentProfilePageData(acct, editPicture);        
    }
    
    // second data has no pictureKey and lots of null fields
    private StudentProfilePageData initializeSecondData() {
        spa = new StudentProfileAttributes("valid.id.2", null, null, null, null, "male", null, "");
        acct = new AccountAttributes("valid.id", "full name", false, "e@mail1.com", "inst", spa);
        pictureUrl = Const.SystemParams.DEFAULT_PROFILE_PICTURE_PATH;
        return new StudentProfilePageData(acct, editPicture);        
    }
    
    private void testProfileEditBox(StudentProfileEditBox profileEditBox) {
        assertEquals(acct.name, profileEditBox.getName());
        assertEquals(editPicture, profileEditBox.getEditPicture());
        assertEquals(convertToEmptyStringIfNull(spa.shortName), profileEditBox.getShortName());
        assertEquals(convertToEmptyStringIfNull(spa.email), profileEditBox.getEmail()); // email comes from SPA, not AA
        assertEquals(convertToEmptyStringIfNull(spa.institute), profileEditBox.getInstitute());
        assertEquals(convertToEmptyStringIfNull(spa.nationality), profileEditBox.getNationality());
        assertEquals(spa.gender, profileEditBox.getGender());
        assertEquals(convertToEmptyStringIfNull(spa.moreInfo), profileEditBox.getMoreInfo());
        assertEquals(acct.googleId, profileEditBox.getGoogleId()); // googleId comes from AA, not SPA
        assertEquals(pictureUrl, profileEditBox.getPictureUrl());
    }

    private void testUploadPhotoModal(StudentProfileUploadPhotoModal uploadPhotoModal) {
        assertEquals(acct.googleId, uploadPhotoModal.getGoogleId());
        assertEquals(pictureUrl, uploadPhotoModal.getPictureUrl());
        assertEquals(spa.pictureKey, uploadPhotoModal.getPictureKey());
    }
    
    // TODO move this to StringHelper
    private String convertToEmptyStringIfNull(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }
    
}
