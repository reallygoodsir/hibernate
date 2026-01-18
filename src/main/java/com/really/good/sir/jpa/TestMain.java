package com.really.good.sir.jpa;

import com.really.good.sir.jpa.advancedmappings.joinedtable.JoinTableMain;
import com.really.good.sir.jpa.advancedmappings.singletable.SingleTableMain;
import com.really.good.sir.jpa.advancedmappings.tableperclass.TablePerClassMain;
import com.really.good.sir.jpa.annotations.advanced.AnnotationsAdvancedMain;
import com.really.good.sir.jpa.annotations.column.AnnotationsColumnMain;
import com.really.good.sir.jpa.annotations.constraints.AnnotationsConstraintsMain;
import com.really.good.sir.jpa.annotations.converter.AnnotationsConverterMain;
import com.really.good.sir.jpa.annotations.enums.AnnotationsEnumsMain;
import com.really.good.sir.jpa.annotations.full.AnnotationsFullMain;
import com.really.good.sir.jpa.annotations.id.AnnotationsIdMain;
import com.really.good.sir.jpa.annotations.lob.AnnotationsLobMain;
import com.really.good.sir.jpa.annotations.queries.AnnotationsQueriesMain;
import com.really.good.sir.jpa.annotations.relationship.AnnotationsRelationshipMain;
import com.really.good.sir.jpa.annotations.temporal.AnnotationsTemporalMain;
import com.really.good.sir.jpa.basic.*;
import com.really.good.sir.jpa.cache.CacheMain;
import com.really.good.sir.jpa.customseq.CustomSequenceGeneratorMain;
import com.really.good.sir.jpa.embeddedpk.EmbeddedPKMain;
import com.really.good.sir.jpa.fullentity.FullEntityMain;
import com.really.good.sir.jpa.id.table.IdTableMain;
import com.really.good.sir.jpa.id.uuid.IdUUIDMain;

public class TestMain {
    public static void main(String[] args) {
        JoinTableMain.main(args);
        SingleTableMain.main(args);
        TablePerClassMain.main(args);
        AnnotationsAdvancedMain.main(args);
        AnnotationsColumnMain.main(args);
        AnnotationsConstraintsMain.main(args);
        AnnotationsConverterMain.main(args);
        AnnotationsEnumsMain.main(args);
        AnnotationsFullMain.main(args);
        AnnotationsIdMain.main(args);
        AnnotationsLobMain.main(args);
        AnnotationsQueriesMain.main(args);
        AnnotationsRelationshipMain.main(args);
        AnnotationsTemporalMain.main(args);
        BasicMain.main(args);
        UserCriteriaMain.main(args);
        UserJpqlMain.main(args);
        UserNamedQueryMain.main(args);
        UserNativeQueryMain.main(args);
        CacheMain.main(args);
        CustomSequenceGeneratorMain.main(args);
        EmbeddedPKMain.main(args);
        FullEntityMain.main(args);
        IdTableMain.main(args);
        IdUUIDMain.main(args);
    }
}
