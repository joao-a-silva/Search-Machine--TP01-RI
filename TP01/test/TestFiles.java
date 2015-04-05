/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Common.ReadFiles;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joao
 */
public class TestFiles {
    
    public TestFiles() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void contentFile1() throws FileNotFoundException{
        String out = FilesTxt.contentFile1;
        String path = FilesTxt.file1;
        ReadFiles f = new ReadFiles();
        assertEquals(out, f.getContentFile(path));        
    }
    
    @Test
    public void contentFile2() throws FileNotFoundException{
        String out = FilesTxt.contentFile2;
        String path = FilesTxt.file2;
        ReadFiles f = new ReadFiles();
        assertEquals(out, f.getContentFile(path));        
    }
    
    @Test
    public void contentFile3() throws FileNotFoundException{
        String out = FilesTxt.contentFile3;
        String path = FilesTxt.file3;
        ReadFiles f = new ReadFiles();
        assertEquals(out, f.getContentFile(path));        
    }
    
    @Test
    public void contentFile4() throws FileNotFoundException{
        String out = FilesTxt.contentFile4;
        String path = FilesTxt.file4;
        ReadFiles f = new ReadFiles();
        assertEquals(out, f.getContentFile(path));        
    }
    
     
    
}
