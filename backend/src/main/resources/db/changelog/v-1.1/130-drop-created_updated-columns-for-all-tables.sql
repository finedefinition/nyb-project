ALTER TABLE `yachts`
DROP COLUMN `updated_at`;

ALTER TABLE `yacht_details`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `owner_infos`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `yacht_images`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `locations`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `towns`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `countries`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `yacht_models`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `fuel_types`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `keel_types`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`;

ALTER TABLE `vessels`
DROP COLUMN `created_by`,
DROP COLUMN `updated_at`,
DROP COLUMN `updated_by`;

GO