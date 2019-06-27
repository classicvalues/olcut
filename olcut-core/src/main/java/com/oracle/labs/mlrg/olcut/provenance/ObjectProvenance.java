package com.oracle.labs.mlrg.olcut.provenance;

import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil.HashType;
import com.oracle.labs.mlrg.olcut.util.Pair;

/**
 * A provenance object which records object fields.
 *
 * Must record the class name of the host object so it can be recovered.
 *
 * All classes which implement this interface must expose a public constructor
 * which accepts a Map&lt;String,Provenance&gt; which is used in deserialisation,
 * and have consistent implementations of equals and hashCode.
 */
public interface ObjectProvenance extends Provenance, Iterable<Pair<String,Provenance>> {

    public static final String CLASS_NAME = "class-name";
    public static final HashType DEFAULT_HASH_TYPE = HashType.SHA256;

    /**
     * Returns the class name of the object which produced this ObjectProvenance instance.
     * @return The class name.
     */
    public String getClassName();

    /**
     * Generates a String representation of this provenance.
     *
     * Commonly used to implement toString.
     * @param name The name to give the provenance.
     * @return A string representation.
     */
    default public String generateString(String name) {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        sb.append("(");
        for (Pair<String,Provenance> p : this) {
            sb.append(p.getA());
            sb.append('=');
            sb.append(p.getB().toString());
            sb.append(',');
        }
        sb.replace(sb.length()-1,sb.length(),")");

        return sb.toString();
    }

}
