package org.ovirt.engine.core.compat;

import java.io.Serializable;
import java.util.UUID;

public class Guid implements Serializable, Comparable<Guid> {

    protected static final String EMPTY_GUID_VALUE = "00000000-0000-0000-0000-000000000000";
    public static final Guid SYSTEM = new Guid("AAA00000-0000-0000-0000-123456789AAA");
    public static final Guid EVERYONE = new Guid("EEE00000-0000-0000-0000-123456789EEE");
    public final static Guid Empty = new Guid();

    /**
     * Needed for the serialization/deserialization mechanism.
     */
    private static final long serialVersionUID = 27305745737022810L;

    private static final byte[] CHANGE_BYTE_ORDER_INDICES = { 3, 2, 1, 0,
            5, 4, 7, 6, 8, 9, 10, 11, 12, 13, 14, 15 };
    private static final byte[] KEEP_BYTE_ORDER_INDICES = { 0, 1, 2, 3,
            4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

    public static Guid NewGuid() {
        return new Guid(UUID.randomUUID());
    }

    public static boolean isNullOrEmpty(Guid id) {
        return id == null || id.equals(Empty);
    }

    protected UUID uuid;

    public Guid() {
        this(EMPTY_GUID_VALUE);

    }

    public Guid(byte[] guid, boolean keepByteOrder) {
        String guidAsStr = getStrRepresentationOfGuid(guid, keepByteOrder);
        if (guidAsStr.isEmpty()) {
            uuid = UUID.fromString(EMPTY_GUID_VALUE);
        } else {
            uuid = UUID.fromString(guidAsStr);
        }
    }

    public Guid(String candidate) {
        if (candidate == null) {
            throw new NullPointerException(
                    "candidate can not be null please use static method createGuidFromString");
        }
        if (candidate.isEmpty()) {
            uuid = UUID.fromString(EMPTY_GUID_VALUE);
        } else {
            uuid = UUID.fromString(candidate);
        }
    }

    public static Guid createGuidFromString(String candidate) {
        if (candidate == null) {
            return null;
        } else {
            return new Guid(candidate);
        }
    }

    public static Guid createGuidFromStringDefaultEmpty(String candidate) {
        if (candidate == null) {
            return Guid.Empty;
        } else {
            return new Guid(candidate);
        }
    }

    public Guid(UUID uuid) {
        this.uuid = uuid;
    }

    public Guid getValue() {
        return this;
    }

    @Override
    public String toString() {
        if (uuid == null)
            return "[null]";
        else
            return uuid.toString();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Guid)) {
            return false;
        }
        Guid otherGuid = (Guid) other;
        return uuid.equals(otherGuid.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    /**
     * Gets a string representation of GUID
     *
     * @param inguid
     *            byte array containing the GUID data.
     * @param keepByteOrder
     *            determines if to keep the byte order in the string representation or not. For some systems as MSSQL
     *            the bytes order should be swapped before converting to String, and for other systems (such as
     *            ActiveDirectory) it should be kept.
     * @return String representation of GUID
     */
    public String getStrRepresentationOfGuid(byte[] inguid,
            boolean keepByteOrder) {

        StringBuilder strGUID = new StringBuilder();

        byte[] byteOrderIndices = null;

        if (keepByteOrder) {
            byteOrderIndices = KEEP_BYTE_ORDER_INDICES;
        } else {
            byteOrderIndices = CHANGE_BYTE_ORDER_INDICES;
        }

        int length = inguid.length;
        // A GUID format looks like xxxx-xx-xx-xx-xxxxxx where each "x"
        // represents a byte in hexadecimal format

        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[0 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[1 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[2 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[3 % length]] & 0xFF));
        strGUID.append("-");
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[4 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[5 % length]] & 0xFF));
        strGUID.append("-");
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[6 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[7 % length]] & 0xFF));
        strGUID.append("-");
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[8 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[9 % length]] & 0xFF));
        strGUID.append("-");
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[10 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[11 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[12 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[13 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[14 % length]] & 0xFF));
        strGUID.append(AddLeadingZero(inguid[byteOrderIndices[15 % length]] & 0xFF));

        return strGUID.toString();

    }

    private static String AddLeadingZero(int k) {
        return (k <= 0xF) ? "0" + Integer.toHexString(k) : Integer
                .toHexString(k);
    }

    @Override
    public int compareTo(Guid other) {
        return this.getUuid().compareTo(other.getUuid());
    }

}
