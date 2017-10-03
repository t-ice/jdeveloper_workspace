package oracle.ide.extsamples.externaltoolimportexport;

import java.io.IOException;

import java.net.URL;

import oracle.ide.config.Preferences;
import oracle.ide.externaltools.ExternalTool;
import oracle.ide.externaltools.ExternalToolManager;
import oracle.ide.marshal.xml.HashStructureIO;

// N.b. internal API. Should make this public.
import oracle.ideimpl.externaltools.ExternalToolList;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;

final class ToolImportExport {
    private static final String NS = 
        "http://xmlns.oracle.com/ide/exported-external-tools";
    private static final String ROOT = "externaltools";
    
    public void importTools( URL url ) throws IOException {
        HashStructure hs = (HashStructure) newIO().load( url );
        ExternalToolList importedTools = ExternalToolList.getInstance( toStorage( hs ) );
        
        for ( ExternalTool tool : importedTools.tools() ) {
            ExternalToolManager.getExternalToolManager().addExternalTool( tool );
        }
    }
    
    public void exportTools( URL url ) throws IOException {
        ExternalToolList tools = ExternalToolList.getInstance( Preferences.getPreferences() );
        HashStructureHolder holder = new HashStructureHolder();
        tools.forcedCopyTo( holder );
        newIO().save( url, holder.getHashStructure() );
    }
            
    private HashStructureIO newIO() {
        return new HashStructureIO( NS, ROOT );
    }
    
    private PropertyStorage toStorage( HashStructure hs ) {
        final HashStructure hsRoot = HashStructure.newInstance();
        hsRoot.putHashStructure( "oracle.ideimpl.externaltools.ExternalToolList", hs );
        
        return new PropertyStorage() {
            public HashStructure getProperties() {
                return hsRoot;
            }
        };
    }
    
    private class HashStructureHolder extends HashStructureAdapter {
        
        public HashStructureHolder() { super( HashStructure.newInstance() ); }
        
        public HashStructure getHashStructure() {
            return _hash;
        }
    }
}
