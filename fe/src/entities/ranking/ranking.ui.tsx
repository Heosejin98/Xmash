import { Route } from "@/pages/ranking";
import { Input, Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import {
  ColumnDef,
  ColumnFiltersState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  SortingState,
  useReactTable,
} from "@tanstack/react-table";
import { ChangeEvent, useEffect, useState } from "react";
import { RankingSchema } from "./ranking.model";
import { RankingQueries } from "./ranking.queries";

const columns: ColumnDef<RankingSchema>[] = [
  {
    accessorKey: "rank",
    header: "#",
  },
  {
    accessorKey: "username",
    header: "Username",
  },
  {
    accessorKey: "tier",
    header: "Tier",
  },
  {
    accessorKey: "lp",
    header: "LP",
  },
];

const useTableFilter = () => {
  const { username } = Route.useSearch();
  const navigate = Route.useNavigate();

  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([
    {
      id: "username",
      value: username ?? "",
    },
  ]);

  const onSearch = (e: ChangeEvent<HTMLInputElement>) => {
    navigate({
      search: (prev) => ({ ...prev, username: e.target.value }),
    });
    setColumnFilters((prev) => ({
      ...prev,
      username: e.target.value,
    }));
  };

  return { onSearch, columnFilters, setColumnFilters };
};

export function LeaderBoardList() {
  const { onSearch, columnFilters, setColumnFilters } = useTableFilter();

  const [sorting, setSorting] = useState<SortingState>(() => []);
  const { data } = useQuery(RankingQueries.rankingQuery());

  const table = useReactTable({
    data: data ?? [],
    columns,
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    onColumnFiltersChange: setColumnFilters,
    getFilteredRowModel: getFilteredRowModel(),
    state: {
      sorting,
      columnFilters,
    },
  });

  useEffect(() => {
    console.log(data);
  }, [data]);

  return (
    <div className="w-full">
      <div className="flex items-center py-4">
        <Input
          placeholder="Filter names..."
          value={(table.getColumn("username")?.getFilterValue() as string) ?? ""}
          onChange={onSearch}
          className="max-w-sm"
        />
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder
                        ? null
                        : flexRender(header.column.columnDef.header, header.getContext())}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id} data-state={row.getIsSelected() && "selected"}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
